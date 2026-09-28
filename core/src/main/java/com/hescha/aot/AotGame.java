package com.hescha.aot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.hescha.aot.assets.GameAssets;
import com.hescha.aot.data.CharacterCatalog;
import com.hescha.aot.data.GameMode;
import com.hescha.aot.persistence.SaveService;
import com.hescha.aot.screen.MainMenuScreen;

public final class AotGame extends Game {
    private CharacterCatalog characters;
    private GameAssets assets;
    private SaveService save;
    private GameMode mode = GameMode.SURVIVAL;

    @Override
    public void create() {
        characters = new CharacterCatalog();
        assets = new GameAssets(characters);
        save = new SaveService(characters);
        assets.playMusic();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void setScreen(Screen next) {
        Screen old = getScreen();
        super.setScreen(next);
        if (old != null && old != next) old.dispose();
    }

    public CharacterCatalog characters() {
        return characters;
    }

    public GameAssets assets() {
        return assets;
    }

    public SaveService save() {
        return save;
    }

    public GameMode mode() {
        return mode;
    }

    public void mode(GameMode mode) {
        this.mode = mode;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (getScreen() != null) getScreen().dispose();
        if (assets != null) assets.dispose();
    }
}
