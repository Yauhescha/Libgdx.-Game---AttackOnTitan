package com.hescha.aot.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class EnemyState {
    public final EnemyType type;
    public final Vector2 position = new Vector2();
    public final Animation<Texture> walk;
    public final Texture hair;
    public final Rectangle body = new Rectangle(), neck = new Rectangle();
    public float width, height, speed, stateTime, deathTime;
    public boolean alive = true;

    public EnemyState(EnemyType type, float x, float y, float w, float h, float speed,
                      Animation<Texture> walk, Texture hair) {
        this.type = type;
        position.set(x, y);
        width = w;
        height = h;
        this.speed = speed;
        this.walk = walk;
        this.hair = hair;
        updateBounds();
    }

    public void updateBounds() {
        body.set(position.x - width * .24f, position.y - height * .48f,
                width * .48f, height * .70f);
        neck.set(position.x - width * .25f, position.y + height * .20f,
                width * .50f, height * .16f);
    }
}
