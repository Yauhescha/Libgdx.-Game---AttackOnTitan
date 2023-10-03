package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import lombok.Data;

@Data
public class Player extends AbstractMovingModel {

    public Player() {
        width = 80;
        height = 120;
        speed = 10;
        texture = new Texture("player/eren/stand.png");
    }

    public void update(float touchX, float touchY) {
        if (touchX > Gdx.graphics.getWidth() / 2) {
            moveRight();
            directionRight=true;
        } else {
            moveLeft();
            directionRight=false;
        }
    }

    private void moveRight() {
        x += speed;
        if (x > SCREEN_WIDTH - width) x = SCREEN_WIDTH - width;
    }

    private void moveLeft() {
        x -= speed;
        if (x < 0) x = 0;
    }
}
