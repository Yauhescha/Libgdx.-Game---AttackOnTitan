package com.hescha.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.Data;

@Data
public abstract class AbstractMovingModel {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speed;
    protected Texture texture;

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
