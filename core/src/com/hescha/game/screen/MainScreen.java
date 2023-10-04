package com.hescha.game.screen;

import static com.hescha.game.util.Settings.SCREEN_HEIGHT;
import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.model.Background;
import com.hescha.game.model.Enemy;
import com.hescha.game.model.Player;
import com.hescha.game.util.EnemyBuilder;

import java.util.List;

public class MainScreen extends ScreenAdapter {
    //static data
    public static final Color BACKGROUND_COLOR = new Color(251f / 255f, 208f / 255f, 153f / 255f, 1);
    public static final int FPS_30 = 1 / 30;

    //required fields
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera camera;

    // game related fields
    private Player player;
    private Enemy enemy;
    private List<Background> backgrounds;

    private float GLOBAL_TIME=0;
    @Override
    public void show() {
        //init required technical fields
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
        camera.update();
        viewport = new FillViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        viewport.apply(true);

        // init game related fields
        player = new Player();
        enemy = EnemyBuilder.randomBuildEnemy();
        backgrounds = Background.getBackgrounds();

        // fill necessary data
        player.setX(SCREEN_WIDTH / 2 - player.getWidth() / 2);
        player.setY(SCREEN_HEIGHT / 3);
    }

    @Override
    public void render(float delta) {
        GLOBAL_TIME+=delta;

        update();

        if(GLOBAL_TIME< FPS_30){return;} else {
            GLOBAL_TIME-= FPS_30;
        }
        draw();
        drawDebug();
    }

    private void update() {
        //update background
        for (Background background : backgrounds) {
            background.update();
        }

        // update player
        boolean touched = Gdx.input.isTouched();
        if (touched) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();
            player.update(touchX, touchY);
        }


        //update enemy
        if (enemy.canMove()) {
            enemy.moveDown();
        }

        boolean intersects = Intersector.overlaps(enemy.getCollisionCircle(), player.getAttackCollider());
        if (intersects && !player.isAnimationPlaying() && enemy.isAlive()) {
            player.playAttackAnimation();
            enemy.setAlive(false);
        }

        if(!enemy.isAlive() || !enemy.canMove()){
            enemy = EnemyBuilder.randomBuildEnemy();
        }
    }

    private void draw() {
        ScreenUtils.clear(BACKGROUND_COLOR);

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        for (Background background : backgrounds) {
            background.draw(batch);
        }
        enemy.draw(batch);
        player.draw(batch);
        batch.end();
    }

    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        enemy.drawDebug(shapeRenderer);
        player.drawDebug(shapeRenderer);

        shapeRenderer.end();
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
