package com.hescha.game;

import com.badlogic.gdx.Game;
import com.hescha.game.screen.MainScreen;

public class AOTGame extends Game {
    public static AOTGame game;
    @Override
    public void create() {
        game=this;
        setScreen(new MainScreen());
    }
}
