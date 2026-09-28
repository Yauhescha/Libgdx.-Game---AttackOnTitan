package com.hescha.aot.system;

import static com.hescha.aot.config.GameConfig.MAX_GAS;

import com.badlogic.gdx.math.Intersector;
import com.hescha.aot.domain.GameSession;

public final class PickupSystem {
    public int update(GameSession session) {
        int collected = 0;
        for (int i = session.gasPickups.size - 1; i >= 0; i--) {
            var pickup = session.gasPickups.get(i);
            if (Intersector.overlaps(pickup.bounds, session.player.body)) {
                session.player.gas = MAX_GAS;
                session.player.gasEndTriggered = false;
                session.gasPickups.removeIndex(i);
                collected++;
            }
        }
        return collected;
    }
}
