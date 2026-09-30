package com.hescha.aot.domain;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import static com.hescha.aot.config.GameConfig.*;

public final class PlayerState {
    public final Vector2 position = new Vector2(WORLD_W / 2f, 180f);
    public final Vector2 velocity = new Vector2();
    public final HookState hook = new HookState();
    public final Rectangle body = new Rectangle();
    public final Circle attack = new Circle();
    public float gas = MAX_GAS, survivalTime, comboTimer;
    public float attackTime = Float.MAX_VALUE, attackDuration = .3f, deathTime, invulnerabilityTime;
    public int kills, coins, combo;
    public boolean alive = true, gasEndTriggered, facingRight = true;

    public void updateBounds(float radius) {
        body.set(position.x - PLAYER_W * .20f, position.y - PLAYER_H * .48f,
                PLAYER_W * .40f, PLAYER_H * .88f);
        attack.set(position.x, position.y, radius);
    }
}
