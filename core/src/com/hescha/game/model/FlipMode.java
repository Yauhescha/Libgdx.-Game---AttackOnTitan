package com.hescha.game.model;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FlipMode {
    NONE(0, 1, 1, 0),
    X(1, 1, 0, 0),
    Y(0, 0, 1, 1),
    X_AND_Y(1, 0, 0, 1);
    final int u, v, u2, v2;
}
