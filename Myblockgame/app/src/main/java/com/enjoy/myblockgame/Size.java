package com.enjoy.myblockgame;

/**
 * Created by yooheeyoung on 2017. 9. 25..
 */

public class Size {

    public int width = 0;
    public int height = 0;


    public Size(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}