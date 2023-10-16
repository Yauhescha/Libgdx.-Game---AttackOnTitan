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
import com.hescha.game.util.PlayerCharacter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends ScreenAdapter {
    //static data
    public static final Color BACKGROUND_COLOR = new Color(251f / 255f, 208f / 255f, 153f / 255f, 1);
    public static final int FPS_30 = 1 / 30;
    final PlayerCharacter playerCharacter;

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

    public GameScreen(PlayerCharacter playerCharacter) {
        this.playerCharacter=playerCharacter;
    }

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

        deathMenuSubscreen = new DeathMenuSubscreen(viewport, batch, playerCharacter);

        // init game related fields
        player = PlayerBuilder.buildPlayer(playerCharacter);
        toRemoveEnemies = new Array<>();
        enemies = new Array<>();
        enemies.add(EnemyBuilder.randomBuildEnemy());
        backgrounds = Background.getBackgrounds();

        // fill necessary data
        player.setX(SCREEN_WIDTH / 2 - player.getWidth() / 2);
        player.setY(SCREEN_HEIGHT / 4);
        player.move(0, 0);
    }

    @Override
    public void render(float delta) {
        GLOBAL_TIME += delta;

        update(delta);
        spawnEnemy();

        if (GLOBAL_TIME < FPS_30) {
            return;
        } else {
            GLOBAL_TIME -= FPS_30;
        }
        draw();
        drawDebug();
    }

    private void spawnEnemy() {
        if (spawnTime < 0) {
            spawnTime = spawnTimeInitial;
            float timesPast = GLOBAL_TIME / spawnTimeInitial;
            if (timesPast == 1) {
                enemies.add(EnemyBuilder.randomBuildEnemy());
            } else if (timesPast == 2) {
                enemies.add(EnemyBuilder.randomBuildEnemy());
                spawnEnemy(1);
            } else if (timesPast == 3) {
                enemies.add(EnemyBuilder.randomBuildEnemy());
                spawnEnemy(2);
            } else {
                enemies.add(EnemyBuilder.randomBuildEnemy());
                spawnEnemy(3);
            }
        }
    }

    private void spawnEnemy(int number) {
        int times = (int) spawnTimeInitial;
        Timer timer = new Timer();
        for (int i = 0; i < number; i++) {
            TimerTask task = new TimerTask() {
                public void run() {
                    enemies.add(EnemyBuilder.randomBuildEnemy());
                }
            };
            int delay = (int) (Math.random() * (times - 1) * 1000); // Задержка между вызовами метода
            timer.schedule(task, delay);
        }
    }


    private void update(float delta) {
        if (!player.isAlive()) {
            return;
        }
        //update background
        for (Background background : backgrounds) {
            background.update();
        }

        // update player
        boolean touched = Gdx.input.isTouched();
        if (touched) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();
            player.move(touchX, touchY);
        }
        player.update(delta);

        //update enemy

        for (Enemy enemy : enemies) {
            checkPlayerAndEnemyInteraction(enemy);
            if (enemy.canMove()) {
                enemy.moveDown();
            } else {
                toRemoveEnemies.add(enemy);
            }

        }
        enemies.removeAll(toRemoveEnemies, true);
        toRemoveEnemies.clear();

        glyphLayout.setText(font, murderCount + "");
        spawnTime -= delta;
    }

    private void checkPlayerAndEnemyInteraction(Enemy enemy) {
        if (!enemy.isAlive()) {
            return;
        }
        boolean deathToAttack = Intersector.overlaps(enemy.getDeathCollision(), player.getAttackCollider());
        if (deathToAttack && !player.isAnimationPlaying()) {
            player.playAttackAnimation();
            enemy.setAlive(false);
            murderCount++;
        }

        boolean bodyToBodyInteraction = enemy.isOverlap(player.getBodyCollider());
        boolean bodyToPartBodyInteraction = enemy.isOverlap(player.getAttackCollider());

        if (bodyToBodyInteraction || bodyToPartBodyInteraction) {
            // player is dead
            player.setAlive(false);
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

        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
        player.draw(batch);
        font.draw(batch, glyphLayout, SCREEN_WIDTH / 2 - glyphLayout.width / 2, SCREEN_HEIGHT - 50);

        batch.end();

        if (!player.isAlive()) {
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
