package com.hescha.aot.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.hescha.aot.AotGame;
import com.hescha.aot.data.GameMode;
import com.hescha.aot.ui.UiButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ModeSelectScreen extends BaseScreen {
    private final GameMode[] modes = GameMode.values();
    private final List<UiButton> buttons = new ArrayList<>();
    private final UiButton back = new UiButton("BACK", 40, 45, 190, 80);

    public ModeSelectScreen(AotGame game) {
        super(game);
        float y = 940;
        for (GameMode mode : modes) {
            buttons.add(new UiButton(mode.title.toUpperCase(Locale.ROOT), 60, y, 600, 85, 1.05f));
            y -= 205;
        }
    }

    @Override public void render(float delta) {
        begin();
        var font = game.assets().font;
        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.15f);
        font.draw(batch, "SELECT MODE", 0, 1180, 720, Align.center, false);
        batch.end();
        for (UiButton button : buttons) button.draw(shapes, batch, font);
        batch.begin();
        font.getData().setScale(.9f);
        font.setColor(.88f, .9f, .93f, 1f);
        for (int i = 0; i < buttons.size(); i++) {
            var button = buttons.get(i);
            // Each description has its own two-line area below its button.
            font.draw(batch, modes[i].description, button.r.x + 20, button.r.y - 20,
                    button.r.width - 40, Align.center, true);
        }
        batch.end();
        font.setColor(Color.WHITE);
        font.getData().setScale(.72f);
        back.draw(shapes, batch, font);
        if (Gdx.input.justTouched()) {
            var point = touch();
            if (back.hit(point)) { game.setScreen(new MainMenuScreen(game)); return; }
            for (int i = 0; i < buttons.size(); i++) if (buttons.get(i).hit(point)) {
                game.mode(modes[i]);
                game.setScreen(new RunScreen(game));
                return;
            }
        }
    }
}
