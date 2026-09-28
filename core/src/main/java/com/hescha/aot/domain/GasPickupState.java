package com.hescha.aot.domain;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public final class GasPickupState {
    public final Vector2 position = new Vector2();
    public final Circle bounds = new Circle();

    public GasPickupState(float x, float y) {
        position.set(x, y);
        bounds.set(x, y, 28f);
    }
}
