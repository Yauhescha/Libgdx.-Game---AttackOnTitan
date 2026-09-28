package com.hescha.aot.system;

import com.hescha.aot.domain.GameSession;

public final class DifficultySystem {
    public void update(GameSession s, float dt) {
        s.player.survivalTime += dt;
        s.difficulty = Math.min(1.45f, s.player.survivalTime / 75f);
        if (s.mode.duration > 0 && s.player.survivalTime >= s.mode.duration) {
            s.finished = true;
            s.won = true;
        }
    }
}
