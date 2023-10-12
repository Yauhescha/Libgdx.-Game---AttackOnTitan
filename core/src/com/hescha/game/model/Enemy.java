package com.hescha.game.model;

import static com.hescha.game.util.EnemyBuilder.deadAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.hescha.game.util.Settings;

import java.util.Random;

import lombok.Data;

@Data
public class Enemy extends AbstractMovingModel {
    private static final Random random = new Random();
    private final Rectangle bodyCollision;
    private final Rectangle deathCollision;
    private boolean isAlive = true;


    float idleStateTime;
    boolean isAnimationPlaying = false;
    private final Animation<Texture> idleAnimation;
    private final Texture hair;

    public Enemy(int width, int height, int speed,
                 Animation<Texture> idleAnimation, Texture hair,
                 Rectangle bodyCollision, Rectangle deathCollision) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.idleAnimation = idleAnimation;
        this.hair = hair;
        this.bodyCollision = bodyCollision;
        this.deathCollision = deathCollision;

        int possibleWayCount = 5;
        y = Settings.SCREEN_HEIGHT;
        x = (int) (random.nextInt(possibleWayCount) * width * 0.7f);
    }

    public void moveDown() {
        y -= speed;
        updateCollisions();
    }

    public boolean canMove() {
        return y + height * 2 >= 0;
    }

    private void updateCollisions() {
        bodyCollision.setX(x + width / 3);
        bodyCollision.setY(y + height / 6);
        deathCollision.setX(x + width / 3.5f);
        deathCollision.setY(y + height * 0.5f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        idleStateTime += Gdx.graphics.getDeltaTime();

        if (isAlive) {
            texture = idleAnimation.getKeyFrame(idleStateTime);
            super.draw(batch);
            batch.draw(hair, x + width / 4, y + height - width / 1.6f, width / 2, width / 2);
        } else {
            texture = deadAnimation.getKeyFrame(idleStateTime);
            super.draw(batch);
        }
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(deathCollision.x, deathCollision.y, deathCollision.width, deathCollision.height);
        shapeRenderer.rect(bodyCollision.x, bodyCollision.y, bodyCollision.width, bodyCollision.height);
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
        idleStateTime = 0;
    }
}
