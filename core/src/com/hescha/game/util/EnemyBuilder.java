package com.hescha.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
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

    private static final Texture[] idleMan1Textures = {
            new Texture("enemy/man1/1.png"),
            new Texture("enemy/man1/2.png")
    };

    private static final Texture[] idleMan2Textures = {
            new Texture("enemy/man2/1.png"),
            new Texture("enemy/man2/2.png")
    };

    private static final Texture[] idleMan3Textures = {
            new Texture("enemy/man3/1.png"),
            new Texture("enemy/man3/2.png")
    };

    private static final Texture[] idleMan4Textures = {
            new Texture("enemy/man4/1.png"),
            new Texture("enemy/man4/2.png")
    };


    private static final Texture[] idleWoman1Textures = {
            new Texture("enemy/woman1/1.png"),
            new Texture("enemy/woman1/2.png")
    };
    private static final Texture[] idleWoman2Textures = {
            new Texture("enemy/woman2/1.png"),
            new Texture("enemy/woman2/2.png")
    };
    private static final Texture[] idleWoman3Textures = {
            new Texture("enemy/woman3/1.png"),
            new Texture("enemy/woman3/2.png")
    };
    private static final Texture[] idleWoman4Textures = {
            new Texture("enemy/woman4/1.png"),
            new Texture("enemy/woman4/2.png")
    };
    private static final float FRAME_DURATION = 0.3f;


    private static final int width = 150;
    private static final int height = 255;
    private static final int speed = 6;
    private static final Rectangle deathCollider = new Rectangle(0, 0, width/2.5f, height / 6);
    private static final Rectangle bodyCollider = new Rectangle(0, 0, width / 3, height*0.7f);

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

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }

//    public static Enemy buildEnemyFat2() {
//        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleFat2Textures);
//        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
//        Enemy enemy = new Enemy(idleAnimation, randomHair());
//
//        return enemy;
//    }
//
//    public static Enemy buildEnemyFat3() {
//        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleFat3Textures);
//        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
//        Enemy enemy = new Enemy(idleAnimation, randomHair());
//
//        return enemy;
//    }
//
    public static Enemy buildEnemyMan1() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleMan1Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }

    public static Enemy buildEnemyMan2() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleMan2Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }

    public static Enemy buildEnemyMan3() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleMan3Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }

    public static Enemy buildEnemyMan4() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleMan4Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }

    public static Enemy buildEnemyWoman1() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleWoman1Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }

    public static Enemy buildEnemyWoman2() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleWoman2Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }

    public static Enemy buildEnemyWoman3() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleWoman3Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }

    public static Enemy buildEnemyWoman4() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleWoman4Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                bodyCollider, deathCollider);
    }


    public static Enemy randomBuildEnemy() {
        int randomNumber = random.nextInt(11);

        switch (randomNumber) {
            case 0:
                return buildEnemyFat1();
//            case 1:
//                return buildEnemyFat2();
//            case 2:
//                return buildEnemyFat3();
            case 3:
                return buildEnemyMan1();
            case 4:
                return buildEnemyMan2();
            case 5:
                return buildEnemyMan3();
            case 6:
                return buildEnemyMan4();
            case 7:
                return buildEnemyWoman1();
            case 8:
                return buildEnemyWoman2();
            case 9:
                return buildEnemyWoman3();
            case 10:
                return buildEnemyWoman4();
            default:
                return buildEnemyFat1();
        }
    }

    private static Texture randomHair() {
        return hairs[random.nextInt(hairs.length)];
    }

}
