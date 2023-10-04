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
    private Animation<Texture> flyingEffectAnimation;
    private final Texture[] flyingEffectTextures = {
            new Texture("effect/flying/flying (1).png"),
            new Texture("effect/flying/flying (2).png"),
            new Texture("effect/flying/flying (3).png"),
            new Texture("effect/flying/flying (4).png")
    };
    private Animation<Texture> attackAnimation;
    private final Texture[] attackTextures = {
            new Texture("player/eren/attack/attack (0).png"),
            new Texture("player/eren/attack/attack (1).png"),
            new Texture("player/eren/attack/attack (2).png")
    };
    private Animation<Texture> attackEffectAnimation;
    private final Texture[] attackEffectTextures = {
            new Texture("player/eren/attackEffect/attackEffect (1).png"),
            new Texture("player/eren/attackEffect/attackEffect (2).png"),
            new Texture("player/eren/attackEffect/attackEffect (3).png")
    };

    private Texture animationFrame;
    private final Rectangle attackCollider;
    float attackStateTime;
    float flyingStateTime;
    boolean isAnimationPlaying = false;

    public Player() {
        width = 80;
        height = 120;
        speed = 10;

        attackCollider = new Rectangle(0, 0, 80, 120);

        texture = new Texture("player/eren/stand.png");

        attackAnimation = new Animation<>(0.1f, attackTextures);
        attackEffectAnimation = new Animation<>(0.1f, attackEffectTextures);
        flyingEffectAnimation = new Animation<>(0.1f, flyingEffectTextures);
        flyingEffectAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void update(float touchX, float touchY) {
        if (touchX > Gdx.graphics.getWidth() / 2) {
            moveRight();
            directionRight = true;
        } else {
            moveLeft();
            directionRight = false;
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
        super.draw(batch);


        if (isAnimationPlaying && !attackAnimation.isAnimationFinished(attackStateTime)) {
            animationFrame = attackAnimation.getKeyFrame(attackStateTime);
            super.draw(animationFrame, batch, FlipMode.getFlipModeByRightDirection(directionRight));
            animationFrame = attackEffectAnimation.getKeyFrame(attackStateTime);
            super.draw(animationFrame, batch, FlipMode.getFlipModeByRightDirection(directionRight));
        } else {
            isAnimationPlaying = false;
        }
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
