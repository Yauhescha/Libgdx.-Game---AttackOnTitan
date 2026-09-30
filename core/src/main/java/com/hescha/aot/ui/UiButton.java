package com.hescha.aot.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class UiButton {
    public final Rectangle r = new Rectangle();
    public final String text;
    private final float textScale;
    private final GlyphLayout label = new GlyphLayout();
    private final Color savedColor = new Color();

    public UiButton(String text, float x, float y, float width, float height) {
        this(text, x, y, width, height, .9f);
    }

    public UiButton(String text, float x, float y, float width, float height, float textScale) {
        this.text = text;
        this.textScale = textScale;
        r.set(x, y, width, height);
    }

    public boolean hit(Vector2 point) { return r.contains(point); }

    public void draw(ShapeRenderer shapes, SpriteBatch batch, BitmapFont font) {
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.10f, .11f, .13f, .94f);
        shapes.rect(r.x, r.y, r.width, r.height);
        shapes.end();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.WHITE);
        shapes.rect(r.x, r.y, r.width, r.height);
        shapes.end();

        float oldScaleX = font.getData().scaleX;
        float oldScaleY = font.getData().scaleY;
        savedColor.set(font.getColor());
        font.getData().setScale(textScale);
        font.setColor(Color.WHITE);
        label.setText(font, text);
        float fit = Math.min(1f, Math.min((r.width - 24) / Math.max(1f, label.width),
                (r.height - 20) / Math.max(1f, label.height)));
        if (fit < 1f) {
            font.getData().setScale(textScale * fit);
            label.setText(font, text);
        }
        batch.begin();
        font.draw(batch, label, r.x + (r.width - label.width) * .5f,
                r.y + (r.height + label.height) * .5f);
        batch.end();
        font.getData().setScale(oldScaleX, oldScaleY);
        font.setColor(savedColor);
    }
}
