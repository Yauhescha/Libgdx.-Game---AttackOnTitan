package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

import lombok.Data;

@Data
public class Enemy extends AbstractMovingModel {
    private static final Random random = new Random();
    private static float collisionRadius=50f;
    private final Circle collisionCircle;
    private boolean isAlive;

    public Enemy() {
        y = Gdx.graphics.getHeight()/2;
        width = 250;
        height = 250;
        speed = 5;
        texture = new Texture("enemy.png");
        collisionCircle = new Circle(x, y, collisionRadius);
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
        isAlive=true;
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x + width / 2);
        collisionCircle.setY(y + height/2+5);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
    }
}
