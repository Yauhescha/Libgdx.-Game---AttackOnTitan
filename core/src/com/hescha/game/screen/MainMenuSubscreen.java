package com.hescha.game.screen;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hescha.game.model.Background;

import java.util.List;

public class MainMenuSubscreen extends ScreenAdapter {
    //static data
    private final OrthographicCamera camera;
    private final SpriteBatch batch;

    private final List<Background> backgrounds;

    public MainMenuSubscreen(OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
        backgrounds = Background.getBackgrounds();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }


    private void update() {
        for (Background background : backgrounds) {
            background.update();
        }
    }

    private void draw() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        for (Background background : backgrounds) {
            background.draw(batch);
        }
        batch.end();
    }
}
