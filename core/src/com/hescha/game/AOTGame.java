package com.hescha.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.hescha.game.screen.GameScreen;

public class AOTGame extends Game {
    public static AOTGame game;

    private static Music music;

    @Override
    public void create() {
        game = this;
        music = Gdx.audio.newMusic(Gdx.files.internal("music/theme.mid"));
        music.play();
        music.setLooping(true);
        setScreen(new GameScreen());
    }
}
