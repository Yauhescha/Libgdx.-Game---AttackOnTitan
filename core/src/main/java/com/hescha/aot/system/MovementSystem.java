package com.hescha.aot.system;

import static com.hescha.aot.config.GameConfig.DRAG;
import static com.hescha.aot.config.GameConfig.GAS_HOOK_DRAIN;
import static com.hescha.aot.config.GameConfig.GAS_IDLE_DRAIN;
import static com.hescha.aot.config.GameConfig.GRAVITY;
import static com.hescha.aot.config.GameConfig.MAX_SPEED;
import static com.hescha.aot.config.GameConfig.WORLD_H;
import static com.hescha.aot.config.GameConfig.WORLD_W;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.hescha.aot.data.CharacterDef;
import com.hescha.aot.domain.PlayerState;

public final class MovementSystem {
    private final Vector2 tmp = new Vector2();

    public void update(PlayerState p, CharacterDef c, float dt) {
        p.gas = Math.max(0, p.gas - GAS_IDLE_DRAIN * dt);
        if (p.gas <= 0) p.hook.stop();
        if (p.hook.active && p.gas > 0) {
            tmp.set(p.hook.anchor).sub(p.position);
            float dist = tmp.len();
            if (dist > 5) {
                tmp.nor().scl(c.hookForce);
                p.velocity.mulAdd(tmp, dt);
            }
            p.gas = Math.max(0, p.gas - GAS_HOOK_DRAIN * dt);
        } else p.velocity.y += GRAVITY * dt;
        float drag = Math.max(0, 1f - DRAG * dt * .32f);
        p.velocity.scl(drag);
        float max = MAX_SPEED * c.moveFactor;
        if (p.velocity.len2() > max * max) p.velocity.setLength(max);
        p.position.mulAdd(p.velocity, dt);
        p.position.x = MathUtils.clamp(p.position.x, 35, WORLD_W - 35);
        p.position.y = MathUtils.clamp(p.position.y, 70, WORLD_H - 80);
        if (p.position.x <= 35 || p.position.x >= WORLD_W - 35) p.velocity.x *= -.3f;
        if (p.position.y <= 70 || p.position.y >= WORLD_H - 80) p.velocity.y *= -.2f;
    }
}
