package com.hescha.game.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlayerCharacter {
    EREN("Eren", new Texture(Gdx.files.internal("player/eren/stand.png"))),
    ARMIN("Armin", new Texture(Gdx.files.internal("player/armin/stand.png"))),
    BERTHOLDT("Bertholdt", new Texture(Gdx.files.internal("player/bertholdt/stand.png")));
    final String name;
    final Texture texture;
}
