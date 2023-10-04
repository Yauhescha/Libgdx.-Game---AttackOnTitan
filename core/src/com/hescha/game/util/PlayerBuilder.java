package com.hescha.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.hescha.game.model.Player;

public class PlayerBuilder {
    private static final Texture[] attackEffectTextures = {
            new Texture("player/eren/attackEffect/attackEffect (1).png"),
            new Texture("player/eren/attackEffect/attackEffect (2).png"),
            new Texture("player/eren/attackEffect/attackEffect (3).png")
    };

    private static final Texture[] erenFlyingEffectTextures = {
            new Texture("effect/flying/flying (1).png"),
            new Texture("effect/flying/flying (2).png"),
            new Texture("effect/flying/flying (3).png"),
            new Texture("effect/flying/flying (4).png")
    };
    private static final Texture[] erenAttackTextures = {
            new Texture("player/eren/attack/attack (0).png"),
            new Texture("player/eren/attack/attack (1).png"),
            new Texture("player/eren/attack/attack (2).png")
    };


    private static final Texture standErenTexture = new Texture("player/eren/stand.png");


    public static Player buildEren() {
        Animation<Texture> attackEffectAnimation = new Animation<>(0.1f, attackEffectTextures);
        Animation<Texture> erenFlyingEffectAnimation = new Animation<>(0.1f, erenFlyingEffectTextures);
        Animation<Texture> erenAttackAnimation = new Animation<>(0.1f, erenAttackTextures);

        int width = 80;
        int height = 120;
        int speed = 10;

        Rectangle attackCollider = new Rectangle(0, 0, width, height / 6);
        Rectangle bodyCollider = new Rectangle(0, 0, width / 3, height);
        Player player = new Player(standErenTexture, width, height, speed,
                erenFlyingEffectAnimation, erenAttackAnimation, attackEffectAnimation,
                bodyCollider, attackCollider);

        return player;
    }
}
