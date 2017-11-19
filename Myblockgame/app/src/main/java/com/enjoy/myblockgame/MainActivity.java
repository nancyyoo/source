package com.enjoy.myblockgame;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFormat(PixelFormat.TRANSLUCENT);

        setContentView(new BufferView(this));

    }

    // 좌표값 바를 움직이는 곳
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}
