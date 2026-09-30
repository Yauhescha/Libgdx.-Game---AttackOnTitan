package com.hescha.aot.system;

import com.badlogic.gdx.math.MathUtils;
import com.hescha.aot.assets.GameAssets;
import com.hescha.aot.domain.*;
import static com.hescha.aot.config.GameConfig.*;

public final class SpawnSystem {
    private final GameAssets assets;
    public SpawnSystem(GameAssets assets) { this.assets = assets; }

    public void update(GameSession session, float dt) {
        session.enemyTimer -= dt;
        session.gasTimer -= dt;
        if (session.enemyTimer <= 0) {
            spawnEnemy(session);
            session.enemyTimer = Math.max(.42f, 1.55f - session.difficulty * .6f);
        }
        if (session.gasTimer <= 0) {
            // At empty gas the next supply descends toward the stranded player.
            float x = session.player.gas <= 0 ? session.player.position.x : MathUtils.random(80, WORLD_W - 80);
            session.gasPickups.add(new GasPickupState(x, MathUtils.random(260, WORLD_H - 220)));
            session.gasTimer = MathUtils.random(5f, 7.5f);
        }
        for (int i = session.gasPickups.size - 1; i >= 0; i--) {
            var canister = session.gasPickups.get(i);
            canister.position.y -= 100f * dt;
            // The sprite is 70 units tall, with up to 4 units of vertical bobbing.
            // Remove only once it has completely passed the bottom of the screen.
            if (canister.position.y < -40f) {
                session.gasPickups.removeIndex(i);
                continue;
            }
            canister.bounds.setPosition(canister.position);
        }
    }

    private void spawnEnemy(GameSession session) {
        EnemyType[] types = EnemyType.values();
        EnemyType type = types[MathUtils.random(types.length - 1)];
        float speed = MathUtils.random(170f, 260f) + session.difficulty * 85f;
        float x = MathUtils.random(95f, WORLD_W - 95f);
        session.enemies.add(new EnemyState(type, x, WORLD_H + TITAN_H * .5f,
                TITAN_W, TITAN_H, speed, assets.titan(type), assets.hair(MathUtils.random(assets.hairCount() - 1))));
    }
}
