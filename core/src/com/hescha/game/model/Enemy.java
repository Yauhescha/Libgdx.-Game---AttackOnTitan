package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import lombok.Data;

@Data
public class Enemy extends AbstractMovingModel {
    private static final Random random = new Random();

    public Enemy() {
        y = Gdx.graphics.getHeight();
        width = 140;
        height = 250;
        speed = 10;
        texture = new Texture("enemy.png");
    }

    public void moveDown() {
        y -= speed;
    }

    public boolean canMove() {
        return y + height * 2 >= 0;
    }

    public void setRandomRunWay() {
        int possibleWayCount = SCREEN_WIDTH / width;
        x = random.nextInt(possibleWayCount) * width;
        y = Gdx.graphics.getHeight();
    }
}