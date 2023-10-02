package com.hescha.game.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Background extends AbstractMovingModel {

    public static List<Background> getBackgrounds() {
        List<Background> list = new ArrayList<>();
        Background b1 = new Background();
        Background b2 = new Background();
        Background b3 = new Background();
        Background b4 = new Background();
        Background b5 = new Background();

        b1.moveToTop(0);
        b2.moveToTop(1);
        b3.moveToTop(2);
        b4.moveToTop(3);
        b5.moveToTop(4);

        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        return list;
    }

    public Background() {
        x = -400;
        y = 0;
        width = 1024 + 512;
        height = 1024;
        speed = 5;
        texture = new Texture("street.png");
    }

    public void update() {
        if (canMove()) {
            moveDown();
        } else {
            moveToTop(3);
        }
    }

    private boolean canMove() {
        return y + height > 0;
    }

    private void moveDown() {
        y -= speed;
    }

    private void moveToTop(int time) {
        y = y + getHeight() * time;
    }

}
