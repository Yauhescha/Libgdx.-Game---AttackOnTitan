package com.hescha.game.screen;

import static com.hescha.game.util.Settings.GAME_PREFERENCE;
import static com.hescha.game.util.Settings.MONEY;
import static com.hescha.game.util.Settings.SKIN;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

    private Preferences prefs;

    @Override
    public void show() {
        prefs = Gdx.app.getPreferences(GAME_PREFERENCE);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal(SKIN));

        EnumSet<PlayerCharacter> characters = EnumSet.allOf(PlayerCharacter.class);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table characterTable = new Table();
        for (PlayerCharacter playerCharacter : characters) {
            Image characterImage = preparePlayerImage(playerCharacter);
            Label modelName = new Label(playerCharacter.getName(), skin);

            Table individualCharacterTable = new Table();
            individualCharacterTable.add(characterImage).pad(10).row();
            individualCharacterTable.add(modelName).pad(10).row();

            boolean isPersonBought = prefs.getBoolean(playerCharacter.name());
            if (!isPersonBought) {
                addBuyButton(playerCharacter, individualCharacterTable);
            } else {
                addChooseButton(playerCharacter, individualCharacterTable);
            }

            characterTable.add(individualCharacterTable).pad(10, 30, 10, 30);

        }

        ScrollPane scrollPane = new ScrollPane(characterTable, skin);
        mainTable.add(scrollPane).fill().expand().row();

        // добавьте здесь другие элементы меню, например кнопку "Play" и "Exit"

        stage.addActor(mainTable);
    }

    private void addChooseButton(PlayerCharacter playerCharacter, Table individualCharacterTable) {
        TextButton chooseButton = new TextButton("Choose", skin);
        chooseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AOTGame.game.setScreen(new GameScreen(playerCharacter));
            }
        });

        individualCharacterTable.add(chooseButton).pad(10).row();
    }

    private void addBuyButton(PlayerCharacter playerCharacter, Table individualCharacterTable) {
        Label price = new Label("Price: " + playerCharacter.getPrice(), skin);
        individualCharacterTable.add(price).pad(10).row();

        int money = prefs.getInteger(MONEY);
        int characterPrice = playerCharacter.getPrice();

        TextButton buyButton = new TextButton("Buy", skin);
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (money >= characterPrice) {
                    prefs.putBoolean(playerCharacter.name(), true);
                    prefs.putInteger(MONEY, money - characterPrice);
                    prefs.flush();
                    individualCharacterTable.removeActor(buyButton);
                }
            }
        });
        individualCharacterTable.add(buyButton).pad(10).row();
    }

    private static Image preparePlayerImage(PlayerCharacter playerCharacter) {
        TextureRegionDrawable drawable = new TextureRegionDrawable(playerCharacter.getTexture());
        drawable.setMinWidth(Gdx.graphics.getWidth() / 4);
        drawable.setMinHeight(Gdx.graphics.getHeight() / 4);
        Image characterImage = new Image(drawable);
        return characterImage;
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