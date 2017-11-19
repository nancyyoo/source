package com.enjoy.mygravityball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by yooheeyoung on 2017. 11. 8..
 */


public class BallResources {

    static public Bitmap bmpBall; static public int r = 80;
    //
//
    static public void set(Context context) {
        bmpBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        bmpBall = Bitmap.createScaledBitmap(bmpBall, r * 2, r * 2, true); }



}
