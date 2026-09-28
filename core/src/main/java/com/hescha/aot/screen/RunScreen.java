package com.hescha.aot.screen;

import static com.hescha.aot.config.GameConfig.HOOK_RANGE;
import static com.hescha.aot.config.GameConfig.MAX_GAS;
import static com.hescha.aot.config.GameConfig.PLAYER_H;
import static com.hescha.aot.config.GameConfig.PLAYER_W;
import static com.hescha.aot.config.GameConfig.WORLD_H;
import static com.hescha.aot.config.GameConfig.WORLD_W;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.hescha.aot.AotGame;
import com.hescha.aot.data.CharacterDef;
import com.hescha.aot.data.GameMode;
import com.hescha.aot.domain.GameSession;
import com.hescha.aot.system.CombatSystem;
import com.hescha.aot.system.DifficultySystem;
import com.hescha.aot.system.EnemyMovementSystem;
import com.hescha.aot.system.MovementSystem;
import com.hescha.aot.system.PickupSystem;
import com.hescha.aot.system.SpawnSystem;
import com.hescha.aot.ui.UiButton;

public final class RunScreen extends BaseScreen {
    private static final float STEP = 1f / 120f;
    private final GameSession session;
    private final CharacterDef character;
    private final MovementSystem movement = new MovementSystem();
    private final SpawnSystem spawn;
    private final EnemyMovementSystem enemyMovement = new EnemyMovementSystem();
    private final PickupSystem pickup = new PickupSystem();
    private final CombatSystem combat = new CombatSystem();
    private final DifficultySystem difficulty = new DifficultySystem();
    private final UiButton menu = new UiButton("MENU", 20, 1190, 140, 65);
    private final UiButton pauseButton = new UiButton("PAUSE", 560, 1190, 140, 65);
    private final UiButton resumeButton = new UiButton("RESUME", 190, 650, 340, 85);
    private final UiButton restart = new UiButton("RESTART", 190, 525, 340, 85);
    private final UiButton overlayMenu = new UiButton("MENU", 190, 415, 340, 85);
    private final Array<SmokePuff> smoke = new Array<>();
    private final Vector2 aim = new Vector2();
    private boolean saved, paused, deathSoundPlayed;
    private float accumulator, smokeTimer, refillFlash, visualTime;

    private static final class SmokePuff {
        float x, y, time;

        SmokePuff(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public RunScreen(AotGame game) {
        super(game);
        session = new GameSession(game.mode());
        character = game.characters().byId(game.save().selectedCharacter());
        session.player.attackDuration = game.assets().characterAttack(character.id).getAnimationDuration();
        session.player.updateBounds(character.attackRadius);
        spawn = new SpawnSystem(game.assets());
    }

    @Override
    public void show() {
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        game.assets().stopMusic();
    }

    private boolean ended() {
        return !session.player.alive || session.finished;
    }

    private boolean playingDeathAnimation() {
        return !session.player.alive && session.player.deathTime
                < game.assets().characterDeath(character.id).getAnimationDuration() + .05f;
    }

    // Return immediately after a screen change: setScreen disposes this screen.
    private boolean input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)
                || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (ended()) {
                finishAndMenu();
                return true;
            }
            setPaused(!paused);
            return false;
        }
        if (Gdx.input.justTouched()) {
            var point = touch();
            if (menu.hit(point)) {
                finishAndMenu();
                return true;
            }
            if (!ended() && pauseButton.hit(point)) {
                setPaused(!paused);
                return false;
            }
            if (paused || ended()) {
                if (!paused && playingDeathAnimation()) return false;
                if (paused && resumeButton.hit(point)) {
                    setPaused(false);
                    return false;
                }
                if (restart.hit(point)) {
                    game.setScreen(new RunScreen(game));
                    return true;
                }
                if (overlayMenu.hit(point)) {
                    finishAndMenu();
                    return true;
                }
                return false;
            }
            if (point.x >= 0 && point.x <= WORLD_W && point.y >= 0 && point.y < 1175
                    && session.player.gas > 0) session.player.hook.active = true;
        }
        var player = session.player;
        if (paused || ended()) {
            player.hook.stop();
            return false;
        }
        if (!Gdx.input.isTouched()) player.hook.stop();
        else if (player.hook.active) {
            aim.set(touch());
            aim.x = MathUtils.clamp(aim.x, 0, WORLD_W);
            aim.y = MathUtils.clamp(aim.y, 0, 1165);
            aim.sub(player.position).limit(HOOK_RANGE);
            player.hook.anchor.set(player.position).add(aim);
        }
        return false;
    }

    private void setPaused(boolean value) {
        paused = value;
        accumulator = 0;
        session.player.hook.stop();
        if (value) game.assets().stopEffects();
    }

    private void finishAndMenu() {
        saveResult();
        game.setScreen(new MainMenuScreen(game));
        game.assets().playMusic();
    }

    private void saveResult() {
        if (saved) return;
        saved = true;
        game.save().addCoins(session.player.coins);
        game.save().saveBest(session.player.survivalTime);
    }

    @Override
    public void render(float delta) {
        if (input()) return;
        if (!paused) {
            accumulator += MathUtils.clamp(delta, 0, .1f);
            while (accumulator >= STEP) {
                update(STEP);
                accumulator -= STEP;
            }
        }
        draw();
    }

    private void update(float dt) {
        updateEffects(dt); // Finish death / evaporation animations after gameplay stops.
        if (ended()) return;
        var player = session.player;
        difficulty.update(session, dt);
        if (session.finished) {
            player.hook.stop();
            saveResult();
            return;
        }
        spawn.update(session, dt);
        movement.update(player, character, dt);
        if (Math.abs(player.velocity.x) > 5) player.facingRight = player.velocity.x > 0;
        enemyMovement.update(session, dt);
        player.updateBounds(character.attackRadius);
        if (pickup.update(session) > 0) refillFlash = 1f;
        boolean wasAttacking = player.attackTime < player.attackDuration;
        int killed = combat.update(session, dt);
        if (killed > 0 && !wasAttacking) game.assets().playAttack(character.id);
        if (player.gas <= 0) player.gasEndTriggered = true;
        if (!player.alive && !deathSoundPlayed) {
            deathSoundPlayed = true;
            game.assets().playDeath(character.id);
            saveResult();
        }
    }

    private void updateEffects(float dt) {
        visualTime += dt;
        refillFlash = Math.max(0, refillFlash - dt);
        var player = session.player;
        if (player.attackTime < player.attackDuration) player.attackTime += dt;
        if (!player.alive) player.deathTime += dt;
        for (int i = session.enemies.size - 1; i >= 0; i--) {
            var enemy = session.enemies.get(i);
            if (enemy.alive) continue;
            enemy.deathTime += dt;
            if (game.assets().evaporation.isAnimationFinished(enemy.deathTime))
                session.enemies.removeIndex(i);
        }
        for (int i = smoke.size - 1; i >= 0; i--) {
            var puff = smoke.get(i);
            puff.time += dt;
            puff.y -= 65f * dt;
            if (puff.time >= game.assets().flightSmoke.getAnimationDuration()) smoke.removeIndex(i);
        }
        boolean flying = !ended() && player.gas > 0 && player.velocity.len2() > 45f * 45f
                && player.position.y > 78f;
        if (flying) {
            smokeTimer -= dt;
            if (smokeTimer <= 0) {
                smoke.add(new SmokePuff(player.position.x, player.position.y - PLAYER_H * .16f));
                smokeTimer += .035f;
            }
        } else smokeTimer = 0;
    }

    private void draw() {
        begin();
        var assets = game.assets();
        var player = session.player;
        batch.begin();
        batch.setColor(1, 1, 1, .65f);
        batch.draw(assets.street, 0, 0, WORLD_W, WORLD_H);
        batch.setColor(Color.WHITE);
        for (var canister : session.gasPickups) {
            float bob = MathUtils.sin(visualTime * 3 + canister.position.x) * 4;
            batch.draw(assets.gasCanister, canister.position.x - 35, canister.position.y - 35 + bob, 70, 70);
        }
        for (var enemy : session.enemies) {
            Texture frame = enemy.alive ? enemy.walk.getKeyFrame(enemy.stateTime, true)
                    : assets.evaporation.getKeyFrame(enemy.deathTime, false);
            batch.draw(frame, enemy.position.x - enemy.width / 2, enemy.position.y - enemy.height / 2,
                    enemy.width, enemy.height);
            if (enemy.alive) {
                // Same hair position and scale as the original renderer.
                batch.draw(enemy.hair, enemy.position.x - enemy.width / 4,
                        enemy.position.y + enemy.height / 2 - enemy.width / 1.7f,
                        enemy.width / 2, enemy.width / 2);
            }
        }
        for (var puff : smoke) {
            float progress = puff.time / assets.flightSmoke.getAnimationDuration();
            float size = 28 + progress * 16;
            batch.setColor(1, 1, 1, 1 - progress * .7f);
            batch.draw(assets.flightSmoke.getKeyFrame(puff.time, false),
                    puff.x - size / 2, puff.y - size / 2, size, size);
        }
        batch.setColor(Color.WHITE);
        batch.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        if (player.alive && player.hook.active) {
            shapes.setColor(Color.LIGHT_GRAY);
            shapes.line(player.position, player.hook.anchor);
            shapes.circle(player.hook.anchor.x, player.hook.anchor.y, 7);
        }
        if (session.mode == GameMode.TRAINING) {
            for (var enemy : session.enemies)
                if (enemy.alive) {
                    shapes.setColor(.95f, .55f, .2f, 1);
                    shapes.rect(enemy.neck.x, enemy.neck.y, enemy.neck.width, enemy.neck.height);
                }
        }
        shapes.end();

        batch.begin();
        boolean attacking = player.attackTime < player.attackDuration;
        Texture playerFrame = !player.alive ? assets.characterDeath(character.id).getKeyFrame(player.deathTime, false)
                : attacking ? assets.characterAttack(character.id).getKeyFrame(player.attackTime, false)
                  : assets.character(character.id);
        if (player.invulnerabilityTime > 0) batch.setColor(1, 1, 1, .6f);
        drawPlayerFrame(playerFrame, PLAYER_W, PLAYER_H);
        batch.setColor(Color.WHITE);
        var attackEffect = assets.characterAttackEffect(character.id);
        if (player.alive && attacking && !attackEffect.isAnimationFinished(player.attackTime))
            drawPlayerFrame(attackEffect.getKeyFrame(player.attackTime, false), PLAYER_W, PLAYER_H);
        batch.end();
        drawHud();
        if (paused || ended()) drawOverlay();
    }

    private void drawPlayerFrame(Texture frame, float width, float height) {
        var player = session.player;
        batch.draw(frame, player.position.x - width / 2, player.position.y - height / 2,
                width, height, player.facingRight ? 0f : 1f, 1f, player.facingRight ? 1f : 0f, 0f);
    }

    private void drawHud() {
        var player = session.player;
        var font = game.assets().font;
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.03f, .04f, .05f, .92f);
        shapes.rect(0, 1167, WORLD_W, 113);
        shapes.setColor(Color.DARK_GRAY);
        shapes.rect(195, 1203, 330, 18);
        shapes.setColor(player.gas < 25 ? Color.SCARLET : Color.CYAN);
        shapes.rect(195, 1203, 330 * MathUtils.clamp(player.gas / MAX_GAS, 0, 1), 18);
        shapes.end();
        batch.begin();
        font.getData().setScale(.48f);
        font.setColor(Color.WHITE);
        font.draw(batch, "GAS " + (int) player.gas + "%", 195, 1260, 330, Align.center, false);
        font.draw(batch, "Kills " + player.kills + "    Coins " + player.coins + "    Time "
                + (int) player.survivalTime + "s", 15, 1150, 690, Align.center, false);
        if (session.mode == GameMode.COMBO)
            font.draw(batch, "COMBO x" + player.combo, 0, 1100, WORLD_W, Align.center, false);
        if (player.alive && player.gasEndTriggered && player.gas <= 0)
            font.draw(batch, "NO GAS - collect a canister", 0, 1055, WORLD_W, Align.center, false);
        else if (refillFlash > 0)
            font.draw(batch, "GAS REFILLED", 0, 1055, WORLD_W, Align.center, false);
        batch.end();
        font.getData().setScale(.52f);
        menu.draw(shapes, batch, font);
        if (!ended()) pauseButton.draw(shapes, batch, font);
        font.getData().setScale(.72f);
    }

    private void drawOverlay() {
        // Let the original death animation finish before placing a panel over it.
        if (!paused && playingDeathAnimation()) return;
        var font = game.assets().font;
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.025f, .03f, .04f, .94f);
        shapes.rect(70, 355, 580, 575);
        shapes.end();
        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(.78f);
        font.draw(batch, paused ? "PAUSED" : session.won ? "MISSION COMPLETE" : "YOU DIED",
                80, 870, 560, Align.center, false);
        font.getData().setScale(.48f);
        font.draw(batch, "Kills: " + session.player.kills + "    Coins: " + session.player.coins
                        + "    Time: " + (int) session.player.survivalTime + "s",
                80, 800, 560, Align.center, false);
        batch.end();
        font.getData().setScale(.65f);
        if (paused) resumeButton.draw(shapes, batch, font);
        restart.draw(shapes, batch, font);
        overlayMenu.draw(shapes, batch, font);
        font.getData().setScale(.72f);
    }

    @Override
    public void pause() {
        if (!ended()) setPaused(true);
        game.assets().stopEffects();
    }

    @Override
    public void hide() {
        session.player.hook.stop();
        game.assets().stopEffects();
        Gdx.input.setCatchKey(Input.Keys.BACK, false);
        saveResult();
    }
}
