package com.hescha.aot.system;

import com.badlogic.gdx.math.Intersector;
import com.hescha.aot.data.GameMode;
import com.hescha.aot.domain.GameSession;

public final class CombatSystem {
    public int update(GameSession session, float dt) {
        var player = session.player;
        if (!player.alive || session.finished) return 0;
        player.invulnerabilityTime = Math.max(0, player.invulnerabilityTime - dt);
        if (player.comboTimer > 0) {
            player.comboTimer -= dt;
            if (player.comboTimer <= 0) player.combo = 0;
        }
        int killed = 0;
        // Resolve nape strikes first, before body collisions with other enemies.
        for (var enemy : session.enemies) {
            if (!enemy.alive) continue;
            // Titans travel down. Approaching the nape from above is a rear strike.
            boolean fromBehind = player.position.y >= enemy.neck.y + enemy.neck.height * .5f;
            if (fromBehind && Intersector.overlaps(player.attack, enemy.neck)) {
                enemy.alive = false;
                enemy.deathTime = 0;
                player.kills++;
                player.combo++;
                player.comboTimer = 2.3f;
                player.coins += session.mode == GameMode.COMBO ? Math.min(5, player.combo) : 1;
                // Keep a running swing intact so all its original frames are visible.
                if (player.attackTime >= player.attackDuration) player.attackTime = 0;
                killed++;
            }
        }
        if (player.invulnerabilityTime > 0) return killed;
        for (var enemy : session.enemies) {
            if (!enemy.alive || !enemy.body.overlaps(player.body)) continue;
            player.hook.stop();
            player.velocity.setZero();
            if (session.mode == GameMode.TRAINING) {
                player.position.set(360, 180);
                player.invulnerabilityTime = 1f;
            } else {
                player.alive = false;
                player.deathTime = 0;
            }
            break;
        }
        return killed;
    }
}
