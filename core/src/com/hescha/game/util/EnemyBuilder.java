package com.hescha.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.hescha.game.model.Enemy;

import java.util.Random;

public class EnemyBuilder {
    private static final Random random = new Random();
    private static final Texture[] idleFat1Textures = {
            new Texture("enemy/fat1/fat1 (1).png"),
            new Texture("enemy/fat1/fat1 (3).png")
    };
    private static final Texture[] idleFat2Textures = {
            new Texture("enemy/fat2/fat2 (1).png"),
            new Texture("enemy/fat2/fat2 (3).png")
    };
    private static final Texture[] idleFat3Textures = {
            new Texture("enemy/fat3/fat3 (1).png"),
            new Texture("enemy/fat3/fat3 (3).png")
    };
    public static final float FRAME_DURATION = 0.3f;


    public static final Texture[] hairs = {
            new Texture("enemy/hair/1.png"),
            new Texture("enemy/hair/2.png"),
            new Texture("enemy/hair/3.png"),
            new Texture("enemy/hair/4.png"),
            new Texture("enemy/hair/5.png"),
            new Texture("enemy/hair/6.png"),
            new Texture("enemy/hair/7.png"),
            new Texture("enemy/hair/8.png"),
            new Texture("enemy/hair/9.png"),
            new Texture("enemy/hair/10.png"),
            new Texture("enemy/hair/11.png"),
            new Texture("enemy/hair/12.png"),
            new Texture("enemy/hair/13.png"),
            new Texture("enemy/hair/14.png"),
            new Texture("enemy/hair/15.png"),
            new Texture("enemy/hair/16.png"),
            new Texture("enemy/hair/17.png"),
            new Texture("enemy/hair/18.png")
    };

    public static Enemy buildEnemyFat1() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleFat1Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
        Enemy enemy = new Enemy(idleAnimation, randomHair());

        return enemy;
    }

    public static Enemy buildEnemyFat2() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleFat2Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
        Enemy enemy = new Enemy(idleAnimation, randomHair());

        return enemy;
    }

    public static Enemy buildEnemyFat3() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleFat3Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
        Enemy enemy = new Enemy(idleAnimation, randomHair());

        return enemy;
    }


    public static Enemy randomBuildEnemy() {
        int randomNumber = random.nextInt(3);

        switch (randomNumber) {
            case 0:
                return buildEnemyFat1();
            case 1:
                return buildEnemyFat2();
            case 2:
                return buildEnemyFat3();
            default:
                return buildEnemyFat1();
        }
    }

    private static Texture randomHair() {
        return hairs[random.nextInt(hairs.length)];
    }

}
