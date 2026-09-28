package com.hescha.aot.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.hescha.aot.AotGame;
import com.hescha.aot.data.GameMode;
import com.hescha.aot.ui.UiButton;

import java.util.ArrayList;
import java.util.List;

public final class ModeSelectScreen extends BaseScreen {
    private final List<UiButton> buttons = new ArrayList<>();
    private final UiButton back = new UiButton("BACK", 20, 40, 190, 70);

    public ModeSelectScreen(AotGame g) {
        super(g);
        float y = 820;
        for (GameMode m : GameMode.values()) {
            buttons.add(new UiButton(m.title.toUpperCase(), 130, y, 460, 90));
            y -= 135;
        }
    }

    @Override
    public void render(float d) {
        begin();
        batch.begin();
        game.assets().font.getData().setScale(1.0f);
        game.assets().font.draw(batch, "SELECT MODE", 0, 1110, 720, Align.center, false);
        game.assets().font.getData().setScale(.60f);
        float y = 785;
        for (GameMode m : GameMode.values()) {
            game.assets().font.draw(batch, m.description, 110, y, 500, Align.center, true);
            y -= 135;
        }
        game.assets().font.getData().setScale(.72f);
        batch.end();
        for (var b : buttons) b.draw(shapes, batch, game.assets().font);
        back.draw(shapes, batch, game.assets().font);
        if (Gdx.input.justTouched()) {
            var p = touch();
            if (back.hit(p)) {
                game.setScreen(new MainMenuScreen(game));
                return;
            }
            for (int i = 0; i < buttons.size(); i++)
                if (buttons.get(i).hit(p)) {
                    game.mode(GameMode.values()[i]);
                    game.assets().stopMusic();
                    game.setScreen(new RunScreen(game));
                    return;
                }
        }
    }
}
