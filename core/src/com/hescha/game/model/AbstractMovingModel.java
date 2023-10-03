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

    protected boolean directionRight = true;
    protected int speed;
    protected Texture texture;

    public void draw(SpriteBatch batch) {
        if (directionRight) {
            draw(batch, FlipMode.NONE);
        } else {
            draw(batch, FlipMode.X);
        }
    }

    public void draw(SpriteBatch batch, FlipMode flip) {
        batch.draw(texture, x, y, width, height, flip.u, flip.v, flip.u2, flip.v2);
    }

    public void draw(Texture texture, SpriteBatch batch, FlipMode flip) {
        batch.draw(texture, x, y, width, height, flip.u, flip.v, flip.u2, flip.v2);
    }
}
