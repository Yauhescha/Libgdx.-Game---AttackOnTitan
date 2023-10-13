package com.hescha.game.model;

import static com.hescha.game.util.EnemyBuilder.deadAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.hescha.game.util.Settings;

import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class Enemy extends AbstractMovingModel {
    private static final Rectangle emptyCollider = new Rectangle(0, 0, 1, 1);
    private static final Random random = new Random();
    private List<Rectangle> bodyCollision;
    private Rectangle deathCollision;
    private boolean isAlive = true;


    float idleStateTime;
    boolean isAnimationPlaying = false;
    private final Animation<Texture> idleAnimation;
    private final Texture hair;

    public Enemy(int width, int height, int speed,
                 Animation<Texture> idleAnimation, Texture hair,
                 List<Rectangle> bodyCollision, Rectangle deathCollision) {
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

        for (Rectangle rectangle : bodyCollision) {
            rectangle.setY(rectangle.getY() + y);
            rectangle.setX(rectangle.getX() + x);
        }
        deathCollision.setY(deathCollision.getY() + y);
        deathCollision.setX(deathCollision.getX() + x);
    }

    public void moveDown() {
        y -= speed;
        updateCollisions();
    }

    public boolean canMove() {
        return y + height * 2 >= 0;
    }

    private void updateCollisions() {
        for (Rectangle rectangle : bodyCollision) {
            rectangle.setY(rectangle.getY() - speed);
        }
        deathCollision.setY(deathCollision.getY() - speed);
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
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(deathCollision.x, deathCollision.y, deathCollision.width, deathCollision.height);
        shapeRenderer.setColor(Color.BLUE);
        for (Rectangle rectangle : bodyCollision) {
            shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
        idleStateTime = 0;
        bodyCollision = List.of();
        deathCollision = emptyCollider;
    }

    public boolean isOverlap(Rectangle rectangle) {
        boolean result = false;
        for (Rectangle temp : bodyCollision) {
            result = Intersector.overlaps(rectangle, temp) || result;
        }
        return result;
    }

    public boolean isOverlap(Circle circle) {
        boolean result = false;
        for (Rectangle rectangle : bodyCollision) {
            result = Intersector.overlaps(circle, rectangle) || result;
        }
        return result;
    }
}
