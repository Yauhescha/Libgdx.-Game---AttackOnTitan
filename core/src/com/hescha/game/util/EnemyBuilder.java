package com.hescha.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.hescha.game.model.Enemy;

import java.util.List;
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


    private static final int width = 200;
    private static final int height = 300;
    private static final int speed = 8;

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

    private static final Texture[] deadTextures = {
            new Texture("effect/dissapearing/1.png"),
            new Texture("effect/dissapearing/2.png"),
            new Texture("effect/dissapearing/3.png"),
            new Texture("effect/dissapearing/4.png"),
            new Texture("effect/dissapearing/5.png"),
            new Texture("effect/dissapearing/6.png"),
            new Texture("effect/dissapearing/7.png"),
            new Texture("effect/dissapearing/8.png"),
            new Texture("effect/dissapearing/9.png"),
            new Texture("effect/dissapearing/10.png"),
            new Texture("effect/dissapearing/11.png"),
            new Texture("effect/dissapearing/12.png"),
            new Texture("effect/dissapearing/13.png"),
            new Texture("effect/dissapearing/14.png")
    };

    public static final Animation<Texture> deadAnimation = new Animation<>(0.08f, deadTextures);


    public static Enemy buildEnemyFat1() {
        int speed1 = speed - 1;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleFat1Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyFat2() {
        int speed1 = speed - 2;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleFat2Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyFat3() {
        int speed1 = speed - 2;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleFat3Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);


        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyMan1() {
        int speed1 = speed + 1;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleMan1Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyMan2() {
        int speed1 = speed + 2;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleMan2Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyMan3() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleMan3Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyMan4() {
        int speed1 = speed + 1;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleMan4Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyWoman1() {
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleWoman1Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyWoman2() {
        int speed1 = speed - 2;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleWoman2Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyWoman3() {
        int speed1 = speed + 2;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleWoman3Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    public static Enemy buildEnemyWoman4() {
        int speed1 = speed + 1;
        Animation<Texture> idleAnimation = new Animation<>(FRAME_DURATION, idleWoman4Textures);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        return new Enemy(width, height, speed1,
                idleAnimation, randomHair(),
                getBodyCollider(), getDeathCollider());
    }

    private static Rectangle getDeathCollider() {
        return new Rectangle(width / 3.5f, height*0.5F, width / 2.5f, height / 6);
    }

    private static List<Rectangle> getBodyCollider() {
        Rectangle rectangle1 = new Rectangle(width / 3, 0, width / 3, height*0.5F);
        Rectangle rectangle2 = new Rectangle(width / 3,height*0.5F+height/6,width / 3,width / 4);
        return List.of(rectangle1, rectangle2);
    }


    public static Enemy randomBuildEnemy() {
        int randomNumber = random.nextInt(11);

        switch (randomNumber) {
            case 0:
                return buildEnemyFat1();
            case 1:
                return buildEnemyFat2();
            case 2:
                return buildEnemyFat3();
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
