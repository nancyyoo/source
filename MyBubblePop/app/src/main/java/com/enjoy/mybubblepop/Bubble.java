package com.enjoy.mybubblepop;

/**
 * Created by yooheeyoung on 2017. 11. 10..
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import java.util.Random;

public class Bubble {

    private int screen_w, screen_h; // 화면의 크기

    // 속도, 뱡향
    private int speed;
    private PointF dir = new PointF();
    private Random rnd = new Random();

    // 비눗방울의 위치, 크기 public float x, y;
    public int r;
    public float x, y;
    public boolean isTouched;

    // 비눗방울 비트맵
    public Bitmap bmpBubble;

    private long currentTime;
    private float deltaTime;

    private Context m_context;
    //-----------------------------
    // constructor
    // create a bubble image
    //-----------------------------
    public Bubble(Context context, int sw, int sh, int x, int y) {
        screen_w = sw; // 화면의 크기
        screen_h = sh;
// 비눗방울의 크기를 랜덤하게 설정
        r = rnd.nextInt(71) + 50; // 50~71
// 비눗방울 만들기
        bmpBubble = BitmapFactory.decodeResource(context.getResources(), );
        bmpBubble = Bitmap.createScaledBitmap(bmpBubble, r * 2, r * 2, true);
        initBubble();
    }


    public boolean hitCheck(float px, float py) { isTouched = false;
        if ((x - px) * (x - px) + (y - py) * (y - py) < r * r)
        {
            int count = rnd.nextInt(6) + 25;
            for (int i = 0; i < count; i++) {
                GameView.smallBubbleArray.add(new SmallBubble(m_context, screen_w, screen_h, px, py)); }
            isTouched = true;
        }
        return isTouched;
    }

    private void initBubble() { // 이동 속도
        speed = rnd.nextInt(51) + 150; // 초속 150~200 픽셀 // 이동 방향 : 0~360도
        double rad = Math.toRadians( rnd.nextInt(360) ); dir.x = (float) Math.cos(rad) * speed;
        dir.y = (float) -Math.sin(rad) * speed;
//초기 위치:화면 전체
        x = rnd.nextInt(screen_w - r * 4) + r * 2;
        y = rnd.nextInt(screen_h - r * 4) + r * 2; currentTime = System.nanoTime();
    }

    //-----------------------------
    // ºñ´°¹æ¿ï ÀÌµ¿
    //-----------------------------
    public void update() {
        // 직전 프레임으로 부터의 경과 시간
        deltaTime = (System.nanoTime() - currentTime) / 1000000000f;
        currentTime = System.nanoTime();
        x += dir.x * deltaTime; // 이동 y += dir.y * deltaTime;
// 화면의 경계에서 반사
        if (x < r || x > screen_w - r) {
            dir.x = -dir.x;
            x += dir.x * deltaTime;
            if (y < r || y > screen_h - r) {
                dir.y = -dir.y;
                y += dir.y * deltaTime;
            }
        }
    }

} // Bubble


