package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import lombok.Data;

@Data
public class Player extends AbstractMovingModel {
    private final Animation<Texture> flyingEffectAnimation;
    private final Animation<Texture> attackAnimation;
    private final Animation<Texture> attackEffectAnimation;
    private final Rectangle attackCollider;

    private Texture animationFrame;
    private float attackStateTime;
    private float flyingStateTime;
    private boolean isAnimationPlaying = false;
    private boolean isAnimationLinePlaying = false;

    private float touchX;
    private float touchY;

    public Player(Texture texture,
                  Animation<Texture> flyingEffectAnimation,
                  Animation<Texture> attackAnimation,
                  Animation<Texture> attackEffectAnimation,
                  Rectangle attackCollider) {
        this.texture = texture;
        this.flyingEffectAnimation = flyingEffectAnimation;
        this.attackAnimation = attackAnimation;
        this.attackEffectAnimation = attackEffectAnimation;
        this.attackCollider = attackCollider;

        width = 80;
        height = 120;
        speed = 10;

        flyingEffectAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void update(float touchX, float touchY) {
        this.touchX = touchX;
        this.touchY = Gdx.graphics.getHeight() - touchY;
        if (touchX > Gdx.graphics.getWidth() / 2) {
            moveRight();
            directionRight = true;
            isAnimationLinePlaying = true;
        } else {
            moveLeft();
            directionRight = false;
            isAnimationLinePlaying = true;
        }
        updateCollisionRectangle();
    }

    private void updateCollisionRectangle() {
        attackCollider.setX(x);
        attackCollider.setY(y);
    }

    private void moveRight() {
        x += speed;
        if (x > SCREEN_WIDTH - width) x = SCREEN_WIDTH - width;
    }

    private void moveLeft() {
        x -= speed;
        if (x < 0) x = 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        attackStateTime += Gdx.graphics.getDeltaTime();
        flyingStateTime += Gdx.graphics.getDeltaTime();


        animationFrame = flyingEffectAnimation.getKeyFrame(flyingStateTime);
        batch.draw(animationFrame, x + width / 4, y - height / 2, width / 2, height);


        if (isAnimationPlaying && !attackAnimation.isAnimationFinished(attackStateTime)) {
            animationFrame = attackAnimation.getKeyFrame(attackStateTime);
            super.draw(animationFrame, batch, FlipMode.getFlipModeByRightDirection(directionRight));
            animationFrame = attackEffectAnimation.getKeyFrame(attackStateTime);
            super.draw(animationFrame, batch, FlipMode.getFlipModeByRightDirection(directionRight));
        } else {
            isAnimationPlaying = false;
            super.draw(batch);
        }
    }

    public void drawIdleLine(ShapeRenderer shapeRenderer) {
        if (isAnimationLinePlaying) {
            isAnimationLinePlaying = false;

            shapeRenderer.setColor(0f, 0f, 0f, 1f); // Черный цвет
            if (directionRight) {
                shapeRenderer.line(x + width / 2, y + height / 2, Gdx.graphics.getWidth(), touchY);
            } else {
                shapeRenderer.line(x + width / 2, y + height / 2, 0, touchY);
            }
        }
    }

    private float calculateY(float x, float y) {
        // Находим уравнение прямой, проходящей через начальные координаты и заданную точку
        // y = (touchY - startY) / (touchX - startX) * (x - startX) + startY
        // Подставляем startX и touchX в формулу, чтобы найти startY и touchY
        float startX = this.x;
        float startY = this.y;

        return (y - startY) / (x - startX) * (Gdx.graphics.getWidth() - startX) + startY;
    }

    public void playAttackAnimation() {
        attackStateTime = 0;
        isAnimationPlaying = true;
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(attackCollider.x, attackCollider.y, attackCollider.width, attackCollider.height);
    }
}
