package com.enjoy.mysnakegame2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.util.Random;

// 시작할 때 방향이 이상함!
public class GameActivity extends Activity {

    Canvas canvas;
    SnakeView snakeView;

    Bitmap headBitmap;
    Bitmap bodyBitmap;
    Bitmap tailBitmap;
    Bitmap appleBitmap;

    Bitmap headleftBitmap;
    Bitmap tailleftBitmap;
    Bitmap bodyleftBitmap;

    Bitmap headcolBitmap;
    Bitmap bodycolBitmap;
    Bitmap tailcolBitmap;

    Bitmap headcolbotBitmap;
    Bitmap tailcolbotBitmap;

    // 꽃 (에니메이션에 사용)
    Bitmap flowerAnimBitmap;

    // 꼬리
    Bitmap tailAnimBitmap;

    // 현재 프레임에 그려질 bitmap 영역
    Rect rectToBeDrawn; // 꽃 어딜그려줄건지
    Rect rectToBeDrawn2;
    Rect rectToBeDrawn3;
    Rect rectToBeDrawn4;
    Rect rectToBeDrawn5;

    // 꼬리 그리기
    Rect TailToBeDrawn;

    // 싱글 프레임 면적
    int frameHeight = 64;
    int frameWidth = 64;
    int numFrames = 2;
    int frameNumber = 0;

    // 싱글 프레임 면적
    int frameTailHeight = 64;
    int frameTailWidth = 64;
    int numTailFrames = 2;
    int frameTailNumber = 0;

    // 소리
    private SoundPool soundPool;
    int sample1 = -1;
    int sample2 = -1;
    int sample3 = -1;
    int sample4 = -1;

    final int D_UP = 0;
    final int D_RIGHT = 1;
    final int D_DOWN = 2;
    final int D_LEFT = 3;

    // 스네이크 이동
    int directionOfTravel = D_UP;

    int screenWidth;
    int screenHeight;
    int topGap;

    // 통계
    long lastFrameTime;
    int fps;
    int score;
    int hi;

    // 게임 오브젝트
    int[] snakeX;
    int[] snakeY;
    int snakeLength;
    int appleX;
    int appleY;

    // 게임 보드 크기 (뱀 크기)
    int blockSize;
    int numBlocksWide;
    int numBlocksHigh;
    int position = 1;

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSound();
        configureDisplay();

        // view 생성
        snakeView = new SnakeView(this);
        setContentView(snakeView);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

    }

    class SnakeView extends SurfaceView implements Runnable {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playingSnake;
        Paint paint;

        public SnakeView(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();

            frameWidth=flowerAnimBitmap.getWidth()/numFrames;
            frameHeight=flowerAnimBitmap.getHeight();

            frameTailWidth=tailAnimBitmap.getWidth()/numTailFrames;
            frameTailHeight=tailAnimBitmap.getHeight();

            // 뱀의 위치를 저장할 배열
            snakeX = new int[200];
            snakeY = new int[200];

            // 스네이크 시작 위치
            getSnake();
            // 사과 위치
            getApple();

        }

        public void getSnake() {
            snakeLength = 3;

            // 스네이크 머리는 화면 중앙으로
            snakeX[0] = numBlocksWide / 2;
            snakeY[0] = numBlocksHigh / 2;

            // 몸통
            snakeX[1] = snakeX[0] - 1;
            snakeY[1] = snakeY[0];

            // 꼬리
            snakeX[2] = snakeX[1] - 1;
            snakeY[2] = snakeY[0];
        }

        public void getApple() {
            Random random = new Random();
            appleX = random.nextInt(numBlocksWide - 1) + 1;
            appleY = random.nextInt(numBlocksHigh - 1) + 1;
        }

//        public void getFlower() {
//            Random random = new Random();
//
//        }

        @Override
        public void run() {
            while (playingSnake) {
                updateGame();
                drawGame();
                Log.d("hy", "onTouchEvent111 : " + position);

                controlFPS();

            }
        }

        public void updateGame() {

            // 그려질 프레임 영역 찾기
            rectToBeDrawn = new Rect((frameNumber * frameWidth), 0,
                    (frameNumber * frameWidth +frameWidth)-1, frameHeight);

            rectToBeDrawn2 = new Rect((frameNumber * frameWidth), 0,
                    (frameNumber * frameWidth +frameWidth)-1, frameHeight);

            rectToBeDrawn3 = new Rect((frameNumber * frameWidth), 0,
                    (frameNumber * frameWidth +frameWidth)-1, frameHeight);

            rectToBeDrawn4 = new Rect((frameNumber * frameWidth), 0,
                    (frameNumber * frameWidth +frameWidth)-1, frameHeight);

            rectToBeDrawn5 = new Rect((frameNumber * frameWidth), 0,
                    (frameNumber * frameWidth +frameWidth)-1, frameHeight);

            TailToBeDrawn = new Rect((frameTailNumber * frameTailWidth), 0,
                    (frameTailNumber * frameTailWidth +frameTailWidth)-1, frameTailHeight);

            // 다음 프레임 찾기
            // 인덳 값 증가
            frameNumber++;
            frameTailNumber++;

            // 5 를 넘어가면 안되니까 ! 최종갯수보다 클 경우 막아줌
            if(frameNumber == numFrames)
            {
                frameNumber = 0;
            }

            if(frameTailNumber == numTailFrames)
            {
                frameTailNumber = 0;
            }

            // 사과를 먹었는지 확인
            if (snakeX[0] == appleX && snakeY[0] == appleY) {
                // 스네이크 길이 증가
                snakeLength++;
                score += 1;
                getApple(); // 애플을 새로운 위치로 조정

                soundPool.play(sample1, 1, 1, 0, 0, 1);
            }


            // 바디 이동 - 꼬리부터
            for (int i = snakeLength; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }


            // 머리를 지정한 방향으로 이동
            switch (directionOfTravel) {
                case D_UP:
                    snakeY[0]--;
                    break;

                case D_RIGHT:
                    snakeX[0]++;
                    break;

                case D_DOWN:
                    snakeY[0]++;
                    break;

                case D_LEFT:
                    snakeX[0]--;
                    break;
            }

            // 문제 발생 확인
            boolean dead = false;

            // 벽 충돌 확인

            if (snakeX[0] == -1) dead = true;
            if (snakeX[0] >= numBlocksWide) dead = true;
            if (snakeY[0] == -1) dead = true;
            if (snakeY[0] >= numBlocksHigh) dead = true;


            // 머리와 몸통 충돌 확인
            for (int i = snakeLength - 1; i > 0; i--) {
                if ((i > 4) && (snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                    dead = true;
                }
            }


            if (dead) {
                // 다시 시작
                soundPool.play(sample4, 1, 1, 0, 0, 1);
                score = 0;
                getSnake();

            }
        }


        public void drawGame() {
            Log.d("hy", "drawGame: " + position);
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                //Paint paint = new Paint();
                canvas.drawColor(Color.argb(255, 206, 251, 201));//the background
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(topGap / 2);
                canvas.drawText("Score:" + score + "  Hi:" + hi, 10, topGap - 6, paint);

                // 벽 라인 그리기
                paint.setStrokeWidth(3);
                canvas.drawLine(1, topGap, screenWidth - 1, topGap, paint);
                canvas.drawLine(screenWidth - 1, topGap, screenWidth - 1, topGap + (numBlocksHigh * blockSize), paint);
                canvas.drawLine(screenWidth - 1, topGap + (numBlocksHigh * blockSize), 1, topGap + (numBlocksHigh * blockSize), paint);
                canvas.drawLine(1, topGap, 1, topGap + (numBlocksHigh * blockSize), paint);

                // 꽃을 그릴 영역 ( 실제 표시하고ㅈ 하는 영역 가운데부분!)
                Rect destRect = new Rect(screenWidth/ 2 - 30, screenHeight/2 -30, screenWidth/2+30, screenHeight/2+30);
                Rect destRect2 = new Rect(screenWidth/3 - 30, screenHeight/3-30, screenWidth/3+30, screenHeight/3+30);
                Rect destRect3 = new Rect(screenWidth/4-30, screenHeight/4-30, screenWidth/4+30, screenHeight/4+30);
                Rect destRect4 = new Rect(screenWidth/5 - 30, screenHeight/5-30, screenWidth/5+30, screenHeight/5+30);
                Rect destRect5 = new Rect(screenWidth/6 - 30, screenHeight/6-30, screenWidth/6+30, screenHeight/6+30);

                // 꽃 그리기
                //recttobedrawn 여기 잘라와서 destrect 여기다 그리겟다
                canvas.drawBitmap(flowerAnimBitmap, rectToBeDrawn, destRect, paint);
                canvas.drawBitmap(flowerAnimBitmap, rectToBeDrawn2, destRect2, paint);
                canvas.drawBitmap(flowerAnimBitmap, rectToBeDrawn3, destRect3, paint);
                canvas.drawBitmap(flowerAnimBitmap, rectToBeDrawn4, destRect4, paint);
                canvas.drawBitmap(flowerAnimBitmap, rectToBeDrawn5, destRect5, paint);
                // 이제 ㄷ꽃 하나 그린거


                switch (directionOfTravel) {
                    case D_UP:
                        canvas.drawBitmap(headcolBitmap, snakeX[0] * blockSize, (snakeY[0] * blockSize) + topGap, paint);
                        //Draw the body
                        for (int i = 1; i < snakeLength - 1; i++) {
                            canvas.drawBitmap(bodycolBitmap, snakeX[i] * blockSize, (snakeY[i] * blockSize) + topGap, paint);
                        }

                        //draw the tail
                        canvas.drawBitmap(tailcolBitmap, snakeX[snakeLength - 1] * blockSize, (snakeY[snakeLength - 1] * blockSize) + topGap, paint);

                        //draw the apple
                        canvas.drawBitmap(appleBitmap, appleX * blockSize, (appleY * blockSize) + topGap, paint);                        break;


                    case D_RIGHT:
                        canvas.drawBitmap(headBitmap, snakeX[0] * blockSize, (snakeY[0] * blockSize) + topGap, paint);
                        //Draw the body
                        for (int i = 1; i < snakeLength - 1; i++) {
                            canvas.drawBitmap(bodyBitmap, snakeX[i] * blockSize, (snakeY[i] * blockSize) + topGap, paint);
                        }
                        //draw the tail
                        canvas.drawBitmap(tailBitmap, snakeX[snakeLength - 1] * blockSize, (snakeY[snakeLength - 1] * blockSize) + topGap, paint);

                        //draw the apple
                        canvas.drawBitmap(appleBitmap, appleX * blockSize, (appleY * blockSize) + topGap, paint);
                        break;

                    case D_DOWN:
                        canvas.drawBitmap(headcolbotBitmap, snakeX[0] * blockSize, (snakeY[0] * blockSize) + topGap, paint);
                        //Draw the body
                        for (int i = 1; i < snakeLength - 1; i++) {
                            canvas.drawBitmap(bodycolBitmap, snakeX[i] * blockSize, (snakeY[i] * blockSize) + topGap, paint);
                        }
                        //draw the tail
                        canvas.drawBitmap(tailcolbotBitmap, snakeX[snakeLength - 1] * blockSize, (snakeY[snakeLength - 1] * blockSize) + topGap, paint);

                        //draw the apple
                        canvas.drawBitmap(appleBitmap, appleX * blockSize, (appleY * blockSize) + topGap, paint);                        break;

                    case D_LEFT:
                        canvas.drawBitmap(headleftBitmap, snakeX[0] * blockSize, (snakeY[0] * blockSize) + topGap, paint);
                        //Draw the body
                        for (int i = 1; i < snakeLength - 1; i++) {
                            canvas.drawBitmap(bodyleftBitmap, snakeX[i] * blockSize, (snakeY[i] * blockSize) + topGap, paint);
                        }
                        //draw the tail
                        canvas.drawBitmap(tailleftBitmap, snakeX[snakeLength - 1] * blockSize, (snakeY[snakeLength - 1] * blockSize) + topGap, paint);

                        //draw the apple
                        canvas.drawBitmap(appleBitmap, appleX * blockSize, (appleY * blockSize) + topGap, paint);
                        break;
                }


                //Draw the snake
//                if (position == 1) {
//
//
//                } else if (position == 2) {
//
//                }
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void controlFPS() {
            long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
            long timeToSleep = 100 - timeThisFrame; // 좀더 빨리 움직이기위해 500대신 100을 넣어줌
            if (timeThisFrame > 0) {
                fps = (int) (1000 / timeThisFrame);
            }
            if (timeToSleep > 0) {

                try {
                    ourThread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                    //Print an error message to the console
                    Log.e("error", "failed to load sound files");
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

        public void resume() {
            playingSnake = true;
            ourThread = new Thread(this);
            ourThread.start();
        }


        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:

                    // 터치 했을 때 스네이크 회전
                    if (motionEvent.getX() > screenWidth / 2) {
                        // turn right

                        directionOfTravel++;

                        if (directionOfTravel == 4)

                            directionOfTravel = D_UP;

                    } else if (motionEvent.getX() < screenWidth / 2) {

                        directionOfTravel--;

                        if (directionOfTravel == -1)
                            directionOfTravel = D_LEFT;
                    }


            }

            return true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        while (true) {
            snakeView.pause();
            break;
        }

        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        snakeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        snakeView.pause();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            snakeView.pause();

            // 메인 화면 보여주기
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();

            return true;
        }
        return false;
    }

    public void loadSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            //Create objects of the 2 required classes
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;

            //create our three fx in memory ready for use
            descriptor = assetManager.openFd("sample1.ogg");
            sample1 = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("sample2.ogg");
            sample2 = soundPool.load(descriptor, 0);


            descriptor = assetManager.openFd("sample3.ogg");
            sample3 = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("sample4.ogg");
            sample4 = soundPool.load(descriptor, 0);


        } catch (IOException e) {
            //Print an error message to the console
            Log.e("error", "failed to load sound files");

        }
    }

    public void configureDisplay() {
        // 스크린 화면
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        topGap = screenHeight / 14;

        blockSize = screenWidth / 40;

        // 게임 블록 수 결정
        // 맨 위는 비워둠
        numBlocksWide = 40;
        numBlocksHigh = ((screenHeight - topGap)) / blockSize;

        // 비트맵 로드

        flowerAnimBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flower_sprite_sheet);
        tailAnimBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tail_sprite_sheet);

        headcolBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.headcol);
        bodycolBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.body_col);
        tailcolBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tailcol);

        headleftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_left);
        tailleftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tailleft);
        bodyleftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bodyleft);

        headcolbotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.headcol_bot);
        tailcolbotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tailcol_bot);

        headBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
        bodyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.body);
        tailBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tail);
        appleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apple);

        // 비트맵을 블록 크기에 맞게 조절

        flowerAnimBitmap = Bitmap.createScaledBitmap(flowerAnimBitmap, blockSize, blockSize, false);
        tailAnimBitmap = Bitmap.createScaledBitmap(tailAnimBitmap,blockSize,blockSize,false);

                headBitmap = Bitmap.createScaledBitmap(headBitmap, blockSize, blockSize, false);
        bodyBitmap = Bitmap.createScaledBitmap(bodyBitmap, blockSize, blockSize, false);
        tailBitmap = Bitmap.createScaledBitmap(tailBitmap, blockSize, blockSize, false);

        headcolbotBitmap = Bitmap.createScaledBitmap(headcolbotBitmap, blockSize, blockSize, false);
        tailcolbotBitmap = Bitmap.createScaledBitmap(tailcolbotBitmap, blockSize, blockSize, false);

        headleftBitmap = Bitmap.createScaledBitmap(headleftBitmap, blockSize, blockSize, false);
        tailleftBitmap = Bitmap.createScaledBitmap(tailleftBitmap, blockSize, blockSize, false);
        bodyleftBitmap = Bitmap.createScaledBitmap(bodyleftBitmap, blockSize, blockSize, false);

        appleBitmap = Bitmap.createScaledBitmap(appleBitmap, blockSize, blockSize, false);
        headcolBitmap = Bitmap.createScaledBitmap(headcolBitmap, blockSize, blockSize, false);
        bodycolBitmap = Bitmap.createScaledBitmap(bodycolBitmap, blockSize, blockSize, false);
        tailcolBitmap = Bitmap.createScaledBitmap(tailcolBitmap, blockSize, blockSize, false);

    }

}
