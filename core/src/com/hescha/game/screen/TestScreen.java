package com.hescha.game.screen;

import static com.hescha.game.util.Settings.SCREEN_HEIGHT;
import static com.hescha.game.util.Settings.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.model.Background;
import com.hescha.game.model.Enemy;
import com.hescha.game.model.Player;
import com.hescha.game.util.EnemyBuilder;
import com.hescha.game.util.FontUtil;
import com.hescha.game.util.PlayerBuilder;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestScreen extends ScreenAdapter {
    //static data
    public static final Color BACKGROUND_COLOR = new Color(251f / 255f, 208f / 255f, 153f / 255f, 1);
    public static final int FPS_30 = 1 / 30;

    private DeathMenuSubscreen deathMenuSubscreen;

    //required fields
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera camera;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    // game related fields
    private Player player;
    private Array<Enemy> enemies;
    private Array<Enemy> toRemoveEnemies;
    private List<Background> backgrounds;

    private float GLOBAL_TIME = 0;
    private final float spawnTimeInitial = 5f;
    private float spawnTime = 5f;
    private int murderCount = 0;

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
        font = FontUtil.generateFont(Color.BLACK);
        glyphLayout = new GlyphLayout();


        deathMenuSubscreen = new DeathMenuSubscreen(viewport, batch);

        // init game related fields
        player = PlayerBuilder.buildRandomPlayer();
        toRemoveEnemies = new Array<>();
        enemies = new Array<>();
        enemies.add(EnemyBuilder.randomBuildEnemy());
        backgrounds = Background.getBackgrounds();

        // fill necessary data
//        player.setX(SCREEN_WIDTH / 2 - player.getWidth() / 2);
//        player.setY(SCREEN_HEIGHT / 4);
        player.move(0, 0);

        Enemy enemy = EnemyBuilder.buildEnemyMan3();
        enemy.setX(SCREEN_WIDTH / 2 - player.getWidth() / 2);
        enemy.setY(SCREEN_HEIGHT / 4);
        enemies.add(enemy);
        enemy.moveDown();
    }

    @Override
    public void render(float delta) {
        GLOBAL_TIME += delta;

//        update(delta);
//        spawnEnemy();

//        if (GLOBAL_TIME < FPS_30) {
//            return;
//        } else {
//            GLOBAL_TIME -= FPS_30;
//        }
        draw();
        drawDebug();
    }



    private void draw() {
        ScreenUtils.clear(BACKGROUND_COLOR);

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        for (Background background : backgrounds) {
            background.draw(batch);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
        player.draw(batch);
        font.draw(batch, glyphLayout, SCREEN_WIDTH / 2 - glyphLayout.width / 2, SCREEN_HEIGHT - 50);

        batch.end();

        if(!player.isAlive()){
            deathMenuSubscreen.draw();
        }
    }

    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Enemy enemy : enemies) {
            enemy.drawDebug(shapeRenderer);
        }
        player.drawDebug(shapeRenderer);
        player.drawIdleLine(shapeRenderer);

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
