package com.hescha.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
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
    private static final Texture[] erenDeadTextures = {
            new Texture("player/eren/dead/1.png"),
            new Texture("player/eren/dead/2.png"),
            new Texture("player/eren/dead/3.png"),
            new Texture("player/eren/dead/4.png"),
            new Texture("player/eren/dead/5.png"),
            new Texture("player/eren/dead/6.png"),
            new Texture("player/eren/dead/7.png")
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
    private static final Texture[] arminDeadTextures = {
            new Texture("player/armin/dead/1.png"),
            new Texture("player/armin/dead/2.png"),
            new Texture("player/armin/dead/3.png"),
            new Texture("player/armin/dead/4.png"),
            new Texture("player/armin/dead/5.png"),
            new Texture("player/armin/dead/6.png")
    };
    private static final Music[] arminDeathSounds = new Music[]{
            Gdx.audio.newMusic(Gdx.files.internal("player/armin/sound/dead1.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("player/armin/sound/dead2.wav"))
    };


    private static final Texture bertholdStandTexture = new Texture("player/bertholdt/stand.png");
    private static final Texture[] bertholdAttackTextures = {
            new Texture("player/bertholdt/attack/1.png"),
            new Texture("player/bertholdt/attack/2.png"),
            new Texture("player/bertholdt/attack/3.png")
    };
    private static final Texture[] bertholdDeadTextures = {
            new Texture("player/bertholdt/dead/1.png"),
            new Texture("player/bertholdt/dead/2.png"),
            new Texture("player/bertholdt/dead/3.png"),
            new Texture("player/bertholdt/dead/4.png"),
            new Texture("player/bertholdt/dead/5.png"),
            new Texture("player/bertholdt/dead/6.png")
    };
    private static final Music[] bertholdDeathSounds = new Music[]{
            Gdx.audio.newMusic(Gdx.files.internal("player/bertholdt/sound/1.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("player/bertholdt/sound/2.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("player/bertholdt/sound/3.wav"))
    };


    public static Player buildEren() {
        Animation<Texture> attackEffectAnimation = new Animation<>(0.1f, attackEffectTextures);
        Animation<Texture> erenFlyingEffectAnimation = new Animation<>(0.1f, flyingEffectTextures);
        Animation<Texture> erenAttackAnimation = new Animation<>(0.1f, erenAttackTextures);
        Animation<Texture> deadAnimation = new Animation<>(0.1f, erenDeadTextures);

        int width = 80;
        int height = 120;
        int speed = 7;

        Rectangle attackCollider = new Rectangle(0, 0, width, height / 6);
        Rectangle bodyCollider = new Rectangle(0, 0, width / 3, height);
        Player player = new Player(erenStandTexture, width, height, speed,
                erenFlyingEffectAnimation, erenAttackAnimation, attackEffectAnimation,
                bodyCollider, attackCollider,
                attackSound, erenDeathSounds, deadAnimation);

        return player;
    }

    public static Player buildArmin() {
        Animation<Texture> attackEffectAnimation = new Animation<>(0.1f, attackEffectTextures);
        Animation<Texture> flyingEffectAnimation = new Animation<>(0.1f, flyingEffectTextures);
        Animation<Texture> attackAnimation = new Animation<>(0.1f, arminAttackTextures);
        Animation<Texture> deadAnimation = new Animation<>(0.1f, arminDeadTextures);

        int width = 80;
        int height = 120;
        int speed = 5;

        Rectangle attackCollider = new Rectangle(0, 0, width, height / 6);
        Rectangle bodyCollider = new Rectangle(0, 0, width / 3, height);
        Player player = new Player(arminStandTexture, width, height, speed,
                flyingEffectAnimation, attackAnimation, attackEffectAnimation,
                bodyCollider, attackCollider,
                attackSound, arminDeathSounds, deadAnimation);

        return player;
    }

    public static Player buildBerthold() {
        Animation<Texture> attackEffectAnimation = new Animation<>(0.1f, attackEffectTextures);
        Animation<Texture> flyingEffectAnimation = new Animation<>(0.1f, flyingEffectTextures);
        Animation<Texture> attackAnimation = new Animation<>(0.1f, bertholdAttackTextures);
        Animation<Texture> deadAnimation = new Animation<>(0.1f, bertholdDeadTextures);

        int width = 80;
        int height = 120;
        int speed = 5;

        Rectangle attackCollider = new Rectangle(0, 0, width, height / 6);
        Rectangle bodyCollider = new Rectangle(0, 0, width / 3, height);
        Player player = new Player(bertholdStandTexture, width, height, speed,
                flyingEffectAnimation, attackAnimation, attackEffectAnimation,
                bodyCollider, attackCollider,
                attackSound, bertholdDeathSounds, deadAnimation);

        return player;
    }


    public static Player buildRandomPlayer() {
        int randomNumber = random.nextInt(3);

        switch (randomNumber) {
            case 0:
                return buildEren();
            case 1:
                return buildArmin();
            case 2:
                return buildBerthold();
            default:
                return buildEren();
        }
    }

    public static Player buildPlayer(PlayerCharacter playerCharacter) {
        switch (playerCharacter) {
            case EREN:
                return buildEren();
            case ARMIN:
                return buildArmin();
            case BERTHOLDT:
                return buildBerthold();
            default:
                return buildEren();
        }
    }
}
