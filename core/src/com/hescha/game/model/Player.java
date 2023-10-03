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
    private Animation<Texture> attackAnimation;
    private Texture animationFrame;
    private final Rectangle attackCollider;
    private final Texture[] attackTextures = {
            new Texture("player/eren/attack/attack (0).png"),
            new Texture("player/eren/attack/attack (1).png"),
            new Texture("player/eren/attack/attack (2).png")
    };
    //    private final Texture[] attackTextures = {
//            new Texture("player/eren/attackEffect/attackEffect (1).png"),
//            new Texture("player/eren/attackEffect/attackEffect (2).png"),
//            new Texture("player/eren/attackEffect/attackEffect (3).png")
//    };
    float stateTime;
    boolean isAnimationPlaying = false;

    public Player() {
        width = 80;
        height = 120;
        speed = 10;

        attackCollider = new Rectangle(0, 0, 80, 120);

        texture = new Texture("player/eren/stand.png");

        attackAnimation = new Animation<>(0.1f, attackTextures);
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
        stateTime += Gdx.graphics.getDeltaTime();
        if (isAnimationPlaying && !attackAnimation.isAnimationFinished(stateTime)) {
            animationFrame = attackAnimation.getKeyFrame(stateTime);
            super.draw(animationFrame, batch, FlipMode.NONE);
        } else {
            isAnimationPlaying = false;
            super.draw(batch);
        }
    }

    public void playAttackAnimation() {
        stateTime = 0;
        isAnimationPlaying = true;
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(attackCollider.x, attackCollider.y, attackCollider.width, attackCollider.height);
    }
}
