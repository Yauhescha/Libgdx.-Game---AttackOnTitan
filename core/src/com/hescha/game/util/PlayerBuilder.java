package com.hescha.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.hescha.game.model.Enemy;
import com.hescha.game.model.Player;

import java.util.Random;

public class PlayerBuilder {
    private static final Random random = new Random();
    private static final Texture[] attackEffectTextures = {
            new Texture("player/eren/attackEffect/attackEffect (1).png"),
            new Texture("player/eren/attackEffect/attackEffect (2).png"),
            new Texture("player/eren/attackEffect/attackEffect (3).png")
    };

    private static final Texture[] flyingEffectTextures = {
            new Texture("effect/flying/flying (1).png"),
            new Texture("effect/flying/flying (2).png"),
            new Texture("effect/flying/flying (3).png"),
            new Texture("effect/flying/flying (4).png")
    };
    private static final Music attackSound = Gdx.audio.newMusic(Gdx.files.internal("player/sound/attack.wav"));


    private static final Texture erenStandTexture = new Texture("player/eren/stand.png");
    private static final Texture[] erenAttackTextures = {
            new Texture("player/eren/attack/attack (0).png"),
            new Texture("player/eren/attack/attack (1).png"),
            new Texture("player/eren/attack/attack (2).png")
    };
    private static final Music[] erenDeathSounds = new Music[]{
            Gdx.audio.newMusic(Gdx.files.internal("player/eren/sound/dead1.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("player/eren/sound/dead2.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("player/eren/sound/dead3.wav"))
    };


    private static final Texture arminStandTexture = new Texture("player/armin/stand.png");
    private static final Texture[] arminAttackTextures = {
            new Texture("player/armin/attack/1.png"),
            new Texture("player/armin/attack/2.png"),
            new Texture("player/armin/attack/3.png")
    };
    private static final Music[] arminDeathSounds = new Music[]{
            Gdx.audio.newMusic(Gdx.files.internal("player/armin/sound/dead1.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("player/armin/sound/dead2.wav"))
    };



    public static Player buildEren() {
        Animation<Texture> attackEffectAnimation = new Animation<>(0.1f, attackEffectTextures);
        Animation<Texture> erenFlyingEffectAnimation = new Animation<>(0.1f, flyingEffectTextures);
        Animation<Texture> erenAttackAnimation = new Animation<>(0.1f, erenAttackTextures);

        int width = 80;
        int height = 120;
        int speed = 7;

        Rectangle attackCollider = new Rectangle(0, 0, width, height / 6);
        Rectangle bodyCollider = new Rectangle(0, 0, width / 3, height);
        Player player = new Player(erenStandTexture, width, height, speed,
                erenFlyingEffectAnimation, erenAttackAnimation, attackEffectAnimation,
                bodyCollider, attackCollider,
                attackSound, erenDeathSounds);

        return player;
    }

    public static Player buildArmin() {
        Animation<Texture> attackEffectAnimation = new Animation<>(0.1f, attackEffectTextures);
        Animation<Texture> flyingEffectAnimation = new Animation<>(0.1f, flyingEffectTextures);
        Animation<Texture> attackAnimation = new Animation<>(0.1f, arminAttackTextures);

        int width = 80;
        int height = 120;
        int speed = 5;

        Rectangle attackCollider = new Rectangle(0, 0, width, height / 6);
        Rectangle bodyCollider = new Rectangle(0, 0, width / 3, height);
        Player player = new Player(arminStandTexture, width, height, speed,
                flyingEffectAnimation, attackAnimation, attackEffectAnimation,
                bodyCollider, attackCollider,
                attackSound, arminDeathSounds);

        return player;
    }


    public static Player buildRandomPlayer() {
        int randomNumber = random.nextInt(2);

        switch (randomNumber) {
            case 0:
                return buildEren();
            case 1:
                return buildArmin();
            default:
                return buildEren();
        }
    }
}
