package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

import lombok.Data;

@Data
public class Enemy extends AbstractMovingModel {
    private static final Random random = new Random();
    private static float collisionRadius = 50f;
    private final Circle collisionCircle;
    private boolean isAlive;


    float idleStateTime;
    boolean isAnimationPlaying = false;
    private Animation<Texture> idleAnimation;
    private final Texture[] idleTextures = {
            new Texture("enemy/fat1/fat1 (1).png"),
            new Texture("enemy/fat1/fat1 (3).png")
    };

    public Enemy() {
        y = Gdx.graphics.getHeight() / 2;
        width = 150;
        height = 255;
        speed = 6;
        texture = new Texture("enemy/enemy.png");
        collisionCircle = new Circle(x, y, collisionRadius);
        idleAnimation = new Animation<>(0.3f, idleTextures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void moveDown() {
        y -= speed;
        updateCollisionCircle();
    }

    public boolean canMove() {
        return y + height * 2 >= 0;
    }

    public void setRandomRunWay() {
        int possibleWayCount = SCREEN_WIDTH / width;
        x = random.nextInt(possibleWayCount) * width;
        y = Gdx.graphics.getHeight();
        updateCollisionCircle();
        isAlive = true;
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
            texture = idleTextures[1];
            super.draw(batch);
        }
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
    }
}
