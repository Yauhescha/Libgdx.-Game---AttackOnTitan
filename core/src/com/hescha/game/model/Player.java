package com.hescha.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hescha.game.util.Settings;

import lombok.Data;

@Data
public class Player {
    private int x;
    private int y;
    private int width = 144;
    private int height = 144;
    private int speed = 10;

    private Texture texture = new Texture("badlogic.jpg");

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
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
        if (x > Settings.SCREEN_WIDTH - width) x = Settings.SCREEN_WIDTH - width;
    }

    private void moveLeft() {
        x -= speed;
        if (x < 0) x = 0;
    }
}
