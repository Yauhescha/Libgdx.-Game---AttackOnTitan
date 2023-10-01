package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_HEIGHT;
import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import lombok.Data;

@Data
public class Enemy extends AbstractMovingModel {
    private static final Random random = new Random();

    public Enemy() {
        y = SCREEN_HEIGHT;
        width = 140;
        height = 250;
        speed = 10;
        texture = new Texture("badlogic.jpg");
    }

    public void moveDown() {
        y -= speed;
    }

    public boolean canMove() {
        return y + height >= 0;
    }

    public void setRandomRunWay() {
        int possibleWayCount = SCREEN_WIDTH / width;
        x = random.nextInt(possibleWayCount) * width;
        y = SCREEN_HEIGHT;
    }
}
