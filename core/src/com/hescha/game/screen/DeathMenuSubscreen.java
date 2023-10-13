package com.hescha.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.AOTGame;
import com.hescha.game.util.FontUtil;

public class DeathMenuSubscreen {
    private final Stage stage;
    private final BitmapFont font;

    public DeathMenuSubscreen(Viewport viewport, SpriteBatch batch ) {
        font = FontUtil.generateFont(Color.BLACK);

        TextButton.TextButtonStyle skin = new TextButton.TextButtonStyle();
        skin.font = font;
        skin.fontColor = Color.BLACK;

        stage = new Stage(viewport, batch);


        Table table = new Table();
        table.setFillParent(true);

        table.reset();

        TextButton restart = new TextButton("Restart", skin);
        table.add(restart).pad(100).row();
        restart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                AOTGame.game.setScreen(new GameScreen());
            }
        });

        TextButton exit = new TextButton("Exit", skin);
        table.add(exit).pad(100).row();
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                Gdx.app.exit();
            }
        });

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    public void draw() {
        stage.draw();
    }

    public void dispose() {
        font.dispose();
        stage.dispose();
    }
}
