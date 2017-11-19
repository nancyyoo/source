package com.enjoy.mysnakegame2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainActivity extends Activity {

    Canvas canvas;
    SnakeAnimView snakeAnimView;

    // 스네이크 머리 (에니메이션에 사용)
    Bitmap headAnimBitmap;

    // 현재 프레임에 그려질 bitmap 영역
    Rect rectToBeDrawn; // 뱀머리에서 어딜그려줄건지

    // 싱글 프레임 면적
    int frameHeight = 64;
    int frameWidth = 64;
    int numFrames = 6;
    int frameNumber = 0;

    int screenWidth;
    int screenHeight;

    //stats
    long lastFrameTime;
    int fps;
    int hi;

    // onTouchEvent 에서 새로운 엑티비티 띄우기
    Intent m_gameIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // 스네이크 비트맵 생성
        headAnimBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_sprite_sheet); // 머리 여섯개 얘 읽어오기


        // 뷰 생성
        snakeAnimView = new SnakeAnimView(this);
        setContentView(snakeAnimView);

        // 게임 창띄우기 용 인텐트 생성
        m_gameIntent = new Intent(this, GameActivity.class);

    }

    class SnakeAnimView extends SurfaceView implements Runnable {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playingSnake;
        Paint paint;

        public SnakeAnimView(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();
            frameWidth=headAnimBitmap.getWidth()/numFrames;
            frameHeight=headAnimBitmap.getHeight();
        }


        @Override
        public void run() {
            while (playingSnake) {
                update();
                draw();
                controlFPS();

            }
        }


        public void update() { // 0,0 64,64 첫번째 영역 찾아주고

            // 그려질 프레임 영역 찾기
            rectToBeDrawn = new Rect((frameNumber * frameWidth), 0,
                    (frameNumber * frameWidth +frameWidth)-1, frameHeight);

            // 다음 프레임 찾기
            // 인덳 값 증가
            frameNumber++;

            // 5 를 넘어가면 안되니까 ! 최종갯수보다 클 경우 막아줌
            if(frameNumber == numFrames)
            {
                frameNumber = 0;
            }



        }

        public void draw() {
            //surface 체크
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(150);
                canvas.drawText("Snake", 10, 150, paint);
                paint.setTextSize(25);
                canvas.drawText("  Hi Score:" + hi, 10, screenHeight-50, paint);

                // 스네이크 머리를 그릴 영역 ( 실제 표시하고ㅈ 하는 영역 가운데부분!)
                Rect destRect = new Rect(screenWidth/2-100, screenHeight/2-100, screenWidth/2+100, screenHeight/2+100);

                // 스네이크 머리 그리기
                //recttobedrawn 여기 잘라와서 destrect 여기다 그리겟다
                canvas.drawBitmap(headAnimBitmap, rectToBeDrawn, destRect, paint);
                // 이제 뱀 머리 하나 그린거

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void controlFPS() {
            long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
            long timeToSleep = 500 - timeThisFrame;

            if (timeThisFrame > 0) {
                fps = (int) (1000 / timeThisFrame);
            }

            if (timeToSleep > 0) { // 2분의 1 시간 되게끔 여기서 조정

                try {
                    ourThread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                }
            }

            lastFrameTime = System.currentTimeMillis();
        }


        public void pause() {
            playingSnake = false;
            try {
                ourThread.join();
            } catch (InterruptedException e) {
            }

        }

        public void resume() { // 화면 뜰 때 호출돼요
            playingSnake = true;
            ourThread = new Thread(this);
            ourThread.start();
        }


        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            // 게임 엑티비티 띄우기
            startActivity(m_gameIntent);

            return true;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        while (true) {
            snakeAnimView.pause();
            break;
        }

        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        snakeAnimView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        snakeAnimView.pause();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            snakeAnimView.pause();
            finish();
            return true;
        }
        return false;
    }


}

