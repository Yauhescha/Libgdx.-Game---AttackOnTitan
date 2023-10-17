package com.hescha.game;

import static com.hescha.game.util.Settings.GAME_PREFERENCE;
import static com.hescha.game.util.Settings.MAX_MURDER_COUNT;
import static com.hescha.game.util.Settings.MONEY;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.hescha.game.screen.MainMenuScreen;
import com.hescha.game.util.PlayerCharacter;

public class AOTGame extends Game {
    public static AOTGame game;

    private static Music music;
    private static Preferences prefs;

    @Override
    public void create() {
        game = this;
        music = Gdx.audio.newMusic(Gdx.files.internal("music/theme.mp3"));
        music.play();
        music.setLooping(true);


        prefs = Gdx.app.getPreferences(GAME_PREFERENCE);

        setScreen(new MainMenuScreen());
    }

    public static int getMoney() {
        return prefs.getInteger(MONEY);
    }

    public static int getMaxMurderedCount() {
        return prefs.getInteger(MAX_MURDER_COUNT);
    }

    public static void updateMoney(int money) {
        prefs.putInteger(MONEY, money);
        prefs.flush();
    }

    public static void updateMaxMurdered(int count) {
        prefs.putInteger(MAX_MURDER_COUNT, count);
        prefs.flush();
    }

    public static boolean isPersonBought(PlayerCharacter playerCharacter) {
        return prefs.getBoolean(playerCharacter.name());
    }

    public static void buyPlayer(PlayerCharacter playerCharacter) {
        prefs.putBoolean(playerCharacter.name(), true);
        prefs.flush();
    }

}
