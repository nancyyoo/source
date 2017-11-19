package com.enjoy.mygravityball;

/**
 * Created by yooheeyoung on 2017. 11. 8..
 */

import android.graphics.Bitmap;
import android.graphics.PointF;

public class Ball {

    private int screenW, screenH;
    private float ground;

    private int speed = 300;
    private int rotationAngle = 120;

    // 중력, 반발 계수
    private float gravity = 1500f;
    private float bounce = 0.8f;

    // 이동 방향
    private PointF dir = new PointF();

    // 공의 위치, 반지름
    public float x, y;
    public int r;

    // 현재 각도, 비트맵, 소멸?
    public float angle;
    public Bitmap bmpBall;
    public boolean isDead;


    public Ball(int width, int height, float px, float py) {
        screenW = width;
        screenH = height;
        x = px;
        y = py;


        bmpBall = BallResources.bmpBall;
        r = BallResources.r;

        ground = screenH * 0.8f;

        dir.x = speed;
        dir.y = 0;


    }


    public void update() {

        angle = rotationAngle * Time.deltaTime;

        dir.y += gravity * Time.deltaTime;

        x += dir.x * Time.deltaTime;

        y += dir.y * Time.deltaTime;

        if(y > ground) {
            y = ground;

            dir.y = -dir.y * bounce;
        }

        isDead = (x > screenW + r);
    }

} // Ball