package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.hescha.game.util.Settings;

import java.util.Random;

import lombok.Data;

@Data
public class Enemy extends AbstractMovingModel {
    private static final Random random = new Random();
    private static float collisionRadius = 50f;
    private final Circle collisionCircle;
    private boolean isAlive=true;


    float idleStateTime;
    boolean isAnimationPlaying = false;
    private final Animation<Texture> idleAnimation;

    public Enemy(Animation<Texture> idleAnimation) {
        this.idleAnimation = idleAnimation;
        width = 150;
        height = 255;
        speed = 6;
        int possibleWayCount = SCREEN_WIDTH / width;
        y = Settings.SCREEN_HEIGHT;
        x = random.nextInt(possibleWayCount) * width;
        collisionCircle = new Circle(x, y, collisionRadius);
    }

    public void moveDown() {
        y -= speed;
        updateCollisionCircle();
    }

    public boolean canMove() {
        return y + height * 2 >= 0;
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x + width / 2);
        collisionCircle.setY(y + height / 2 + 5);
    }

    @Override
    public void draw(SpriteBatch batch) {
        idleStateTime += Gdx.graphics.getDeltaTime();

        if (isAlive) {
            texture = idleAnimation.getKeyFrame(idleStateTime);
            super.draw(batch);
        } else {
            texture = idleAnimation.getKeyFrame(0);
            super.draw(batch);
        }
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
    }
}
