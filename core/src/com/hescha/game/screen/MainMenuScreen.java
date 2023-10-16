package com.hescha.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hescha.game.AOTGame;
import com.hescha.game.util.PlayerCharacter;

import java.util.EnumSet;

public class MainMenuScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private EnumSet<PlayerCharacter> characters;
    private ScrollPane scrollPane;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("style.json"));

        characters = EnumSet.allOf(PlayerCharacter.class);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table characterTable = new Table();
        for (PlayerCharacter playerCharacter : characters) {
            TextureRegionDrawable drawable = new TextureRegionDrawable(playerCharacter.getTexture());
            drawable.setMinWidth(Gdx.graphics.getWidth() / 4);
            drawable.setMinHeight(Gdx.graphics.getHeight() / 4);
            Image characterImage = new Image(drawable);

            Label modelName = new Label(playerCharacter.getName(), skin);
            TextButton buyButton = new TextButton("Buy", skin);
            TextButton chooseButton = new TextButton("Choose", skin);

            buyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
//                    if (!playerCharacter.isPurchased) {
                        // реализуйте процесс покупки
//                    }
                }
            });

            chooseButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                        AOTGame.game.setScreen(new GameScreen(playerCharacter));
                }
            });

            Table individualCharacterTable = new Table();
            individualCharacterTable.add(characterImage).pad(10).row();
            individualCharacterTable.add(modelName).pad(10).row();
            individualCharacterTable.add(buyButton).pad(10).row();
            individualCharacterTable.add(chooseButton).pad(10).row();

            characterTable.add(individualCharacterTable).pad(10);

        }

        scrollPane = new ScrollPane(characterTable, skin);
        mainTable.add(scrollPane).fill().expand().row();

        // добавьте здесь другие элементы меню, например кнопку "Play" и "Exit"

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}