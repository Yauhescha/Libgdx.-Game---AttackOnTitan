package com.hescha.aot.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public final class UiButton {
    public final Rectangle r = new Rectangle();
    public final String text;

    public UiButton(String text, float x, float y, float w, float h) {
        this.text = text;
        r.set(x, y, w, h);
    }

    public boolean hit(Vector2 p) {
        return r.contains(p);
    }

    public void draw(ShapeRenderer s, SpriteBatch b, BitmapFont f) {
        s.begin(ShapeRenderer.ShapeType.Filled);
        s.setColor(new Color(.10f, .11f, .13f, .94f));
        s.rect(r.x, r.y, r.width, r.height);
        s.end();
        s.begin(ShapeRenderer.ShapeType.Line);
        s.setColor(Color.WHITE);
        s.rect(r.x, r.y, r.width, r.height);
        s.end();
        b.begin();
        f.setColor(Color.WHITE);
        f.draw(b, text, r.x, r.y + r.height * .66f, r.width, Align.center, false);
        b.end();
    }
}
