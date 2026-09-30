package com.hescha.aot.system;

import com.hescha.aot.domain.GameSession;
import static com.hescha.aot.config.GameConfig.*;

public final class EnemyMovementSystem {
    public void update(GameSession session, float dt) {
        for (int i = session.enemies.size - 1; i >= 0; i--) {
            var enemy = session.enemies.get(i);
            if (!enemy.alive) continue; // Evaporation is advanced even after the run ends.
            enemy.stateTime += dt;
            float dx = session.player.position.x - enemy.position.x;
            enemy.position.x += Math.signum(dx) * Math.min(Math.abs(dx), enemy.speed * .16f * dt);
            enemy.position.y -= enemy.speed * dt;
            enemy.updateBounds();
            if (enemy.position.y < -TITAN_H) session.enemies.removeIndex(i);
        }
    }
}
