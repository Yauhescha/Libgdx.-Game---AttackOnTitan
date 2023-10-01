package com.hescha.game.model;

import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import lombok.Data;

@Data
public class Player extends AbstractMovingModel {

    public Player() {
        width = 144;
        height = 144;
        speed = 10;
        texture = new Texture("badlogic.jpg");
    }

    public void update(float touchX, float touchY) {
        // Преобразуем координаты касания в относительные к координатной сетке игры
        float gameX = touchX / Gdx.graphics.getWidth() * Gdx.graphics.getWidth();
        float gameY = touchY / Gdx.graphics.getHeight() * Gdx.graphics.getHeight();

        if (gameX > x + width) {
            moveRight();
        }

        // Проверяем, находится ли касание левее персонажа
        if (gameX < x) {
            moveLeft();
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
