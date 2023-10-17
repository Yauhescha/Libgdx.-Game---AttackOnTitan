package com.hescha.game.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlayerCharacter {
    ARMIN("Armin", 5, 0,
            new Texture(Gdx.files.internal("player/armin/stand.png"))),
    BERTHOLDT("Bertholdt", 6, 10,
            new Texture(Gdx.files.internal("player/bertholdt/stand.png"))),
    EREN("Eren", 7, 50,
            new Texture(Gdx.files.internal("player/eren/stand.png")));
    final String name;
    final int speed;
    final int price;
    final Texture texture;
}
