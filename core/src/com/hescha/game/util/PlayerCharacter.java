package com.hescha.game.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlayerCharacter {
    ARMIN("Armin", 3, 0, 80,
            new Texture(Gdx.files.internal("player/armin/stand.png"))),
    BERTHOLDT("Bertholdt", 5, 20, 60,
            new Texture(Gdx.files.internal("player/bertholdt/stand.png"))),
    EREN("Eren", 7, 80, 70,
            new Texture(Gdx.files.internal("player/eren/stand.png")));
    final String name;
    final int speed;
    final int price;
    final int attackRadius;
    final Texture texture;
}
