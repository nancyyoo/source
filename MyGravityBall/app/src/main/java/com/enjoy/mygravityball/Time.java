package com.enjoy.mygravityball;

/**
 * Created by yooheeyoung on 2017. 11. 8..
 */

public class Time {
    static private long currentTime = System.nanoTime();
    static public float deltaTime;

    static public void update() {
        deltaTime = (System.nanoTime() - currentTime) / 1000000000f;
        currentTime = System.nanoTime();
    }

} // Time