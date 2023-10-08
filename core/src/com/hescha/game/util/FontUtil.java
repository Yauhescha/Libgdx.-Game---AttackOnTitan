package com.hescha.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtil {

    public static final String FONT_NAME = "font.ttf";

    public static BitmapFont generateFont(Color color, int size) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_NAME));
        BitmapFont font = generator.generateFont(parameter);

        generator.dispose();

        return font;
    }

    public static BitmapFont generateFont(Color color) {
        return generateFont(color, 50);
    }
}
