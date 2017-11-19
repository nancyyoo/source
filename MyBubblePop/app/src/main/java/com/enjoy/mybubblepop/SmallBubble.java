package com.enjoy.mybubblepop;

/**
 * Created by yooheeyoung on 2017. 11. 10..
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import java.util.Random;


public class SmallBubble {
    private int screen_w, screen_h;

    private int speed;
    private PointF dir = new PointF();
    private float life;

    public float x, y;
    public int r;
    public Bitmap bmpBubble;

    public int alpha = 255;
    public boolean isDone = false;


    public SmallBubble(Context context, int sw, int sh, float px, float py) {
        screen_w = sw;     // È­¸éÀÇ Å©±â
        screen_h = sh;
        x = px;
        y = py;

        Random rnd = new Random();

        speed = rnd.nextInt(201) + 300;     // 300~500
        life = rnd.nextInt(6) + 10 / 10f; // 1~1.5 sec

        // ÀÌµ¿ ¹æÇâ : 0~360µµ
        double rad = Math.toRadians( rnd.nextInt(360) );

        dir.x = (float) Math.cos(rad) * speed;
        dir.y = (float) -Math.sin(rad) * speed;

        r = rnd.nextInt(11) + 10; // 10~20;
        int n = rnd.nextInt(6);   // number of bubble

        // ºñ´°¹æ¿ï ¸¸µé±â
        bmpBubble = BitmapFactory.decodeResource(context.getResources(), R.drawable.b0 + n);
        bmpBubble = Bitmap.createScaledBitmap(bmpBubble, r * 2, r * 2, true);
    }


    public void update() {

        x += dir.x * Time.deltaTime;     // ÀÌµ¿
        y += dir.y * Time.deltaTime;

        life -= Time.deltaTime;
        if (life < 0) {
            alpha -= 5;
            if (alpha < 0) alpha = 0;
        }

        // È­¸éÀ» ¹þ¾î³µ´Â°¡?
        if (alpha == 0 || x < -r || x > screen_w + r || y < -r || y > screen_h + r) {
            isDone = true;
        }
    }
}
