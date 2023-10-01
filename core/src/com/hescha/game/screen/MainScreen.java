package com.hescha.game.screen;

import static com.hescha.game.util.Settings.SCREEN_HEIGHT;
import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.model.Player;

public class MainScreen extends ScreenAdapter {
    //static data
    public static final Color BACKGROUND_COLOR = new Color(251f / 255f, 208f / 255f, 153f / 255f, 1);

    //required fields
    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;

    // game related fields
    private Player player;

    @Override
    public void show() {
        //init required technical fields
        batch = new SpriteBatch();
        camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        viewport.apply(true);

        // init game related fields
        player = new Player();

        // fill necessary data
        player.setX(SCREEN_WIDTH / 2 - player.getWidth() / 2);
        player.setY(SCREEN_HEIGHT / 3);
    }

    @Override
    public void render(float delta) {
        update();

        draw();
    }

    private void update() {
        // Определяем координаты касания пользователя
        boolean touched = Gdx.input.isTouched();
        if (touched) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();
            player.update(touchX, touchY);
        }
    }

    private void draw() {
        ScreenUtils.clear(BACKGROUND_COLOR);

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        player.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
