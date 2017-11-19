package com.enjoy.mybubblepop;

/**
 * Created by yooheeyoung on 2017. 11. 10..
 */

public class Time {
    static private long currentTime = System.nanoTime();
    static public float deltaTime;

    static public void update() {
        deltaTime = (System.nanoTime() - currentTime) / 1000000000f;
        currentTime = System.nanoTime();
    }

} // Time