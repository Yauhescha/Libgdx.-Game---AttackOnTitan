package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.hescha.game.util.LimitedQueue;

import lombok.Data;

@Data
public class Player extends AbstractMovingModel {
    private final LimitedQueue<Point> fifo = new LimitedQueue<>(40);
    private final Animation<Texture> flyingEffectAnimation;
    private final Animation<Texture> attackAnimation;
    private final Animation<Texture> attackEffectAnimation;
    private final Rectangle bodyCollider;
    private final Rectangle attackCollider;
    private final Music erenAttackSound;
    private final Music[] erenDeathSounds;

    private Texture animationFrame;
    private float attackStateTime;
    private float flyingStateTime;
    private boolean isAnimationPlaying = false;
    private boolean isAnimationLinePlaying = false;
    private boolean isSoundAttackPlaying = false;

    private float touchX;
    private float touchY;

    public Player(Texture texture,
                  int width, int height, int speed,
                  Animation<Texture> flyingEffectAnimation,
                  Animation<Texture> attackAnimation,
                  Animation<Texture> attackEffectAnimation,
                  Rectangle bodyCollider,
                  Rectangle attackCollider,
                  Music erenAttackSound, Music[] erenDeathSounds) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.flyingEffectAnimation = flyingEffectAnimation;
        this.attackAnimation = attackAnimation;
        this.attackEffectAnimation = attackEffectAnimation;
        this.bodyCollider = bodyCollider;
        this.attackCollider = attackCollider;
        this.erenAttackSound = erenAttackSound;
        this.erenDeathSounds = erenDeathSounds;
    }

    public void update(float delta) {
        attackStateTime += delta;
        flyingStateTime += delta;
        updateCollisionRectangle();
        fifo.forEach(point -> point.y -= 10);
        if(isSoundAttackPlaying){
            isSoundAttackPlaying=false;
            erenAttackSound.play();
        }
    }

    public void move(float touchX, float touchY) {
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
        attackCollider.setY(y + height / 3);

        bodyCollider.setX(x + width / 3);
        bodyCollider.setY(y);
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
        animationFrame = flyingEffectAnimation.getKeyFrame(flyingStateTime, true);
        fifo.add(new Point(x + width / 4, y + height / 2, animationFrame));
        fifo.forEach(point -> batch.draw(point.texture,
                point.getX(), point.getY(),
                width / 3, width / 3));


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

    public void playAttackAnimation() {
        attackStateTime = 0;
        isAnimationPlaying = true;
        isSoundAttackPlaying = true;
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(attackCollider.x, attackCollider.y, attackCollider.width, attackCollider.height);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(bodyCollider.x, bodyCollider.y, bodyCollider.width, bodyCollider.height);
    }
}
