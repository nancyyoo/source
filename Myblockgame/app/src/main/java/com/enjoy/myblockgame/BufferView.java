package com.enjoy.myblockgame;

/**
 * Created by yooheeyoung on 2017. 9. 25..
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import static com.enjoy.myblockgame.R.drawable.block1;
import static com.enjoy.myblockgame.R.drawable.block2;


public class BufferView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private final int MAX_BALL = 1;
    private final int MAX_SPEED = 20;

    private Thread thread;
    private Bitmap[] ball;
    private int ballWidth;
    private int ballHeight;
    private Random mRandom;
    private int score;
    private int lives;

    Vector<Block> mListBlock = new Vector<Block>(); // 블록

    Rect m_rectFrame = new Rect();

    /**
     * 비트맵의 최초 x좌표
     */
    private float x = 0.0f;
    /**
     * 비트맵의 최초 y좌표
     */
    private float y = 0.0f;
    /**
     * 지난 터치의 x좌표
     */
    private float prevX = -1;
    /**
     * 지난 터치의 y좌표
     */
    private float prevY = -1;
// 데이터를 저장해서 이미지 갖고온거 바 공 블록 이미지 한번에

    public BufferView(Context context) {
        super(context);
        this.setFocusable(true);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { // 비트맵 이미지 사이라 화면 전부에 꽉참
        // 그래서 이미지 변경해주기
        super.onSizeChanged(w, h, oldw, oldh);
        mRandom = new Random(System.currentTimeMillis());

        Bitmap bitmap;
        SurfaceHolder holder = this.getHolder();

        ball = new Bitmap[MAX_BALL];
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ball);
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, false);

        ballWidth = bitmap.getWidth();
        ballHeight = bitmap.getHeight();

        ball[0] = transparentBitmap(bitmap, ballWidth, ballHeight);
        for (int i = 1; i != ball.length; ++i) {
            ball[i] = ball[0];
        }

        holder.addCallback(this);
        holder.setFixedSize(this.getWidth(), this.getHeight());

        m_rectFrame.set(0, 0, this.getWidth(), this.getHeight());

        initBlock();
    }

    void initBlock() {
        mListBlock = new Vector<Block>();

        Drawable drawableDefense = (Drawable) this.getResources().getDrawable(R.drawable.block5);

        Point center = new Point(m_rectFrame.width() / 2, m_rectFrame.height() / 2);

        Size blockSize = new Size(drawableDefense.getIntrinsicWidth(), drawableDefense.getIntrinsicHeight());

        Block defenseBlock = new Block();
        defenseBlock.setXY(center.x - blockSize.width / 2, this.m_rectFrame.height() - 100);
        defenseBlock.setWidth(blockSize.width * 2);
        defenseBlock.setHeight(blockSize.height);
        defenseBlock.setDrawable(drawableDefense);
        this.mListBlock.add(defenseBlock);


        int countX = m_rectFrame.width() / blockSize.width;
        int countY = m_rectFrame.height() / blockSize.height / 8;
        int margin = (m_rectFrame.width() - blockSize.width * countX) / 2;

        // 이미지를 가져와서 리스트에 추가해주기
        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                Block block = new Block();
                int rand = getRandom(0, 3);

                block.setDrawable(this.getResources().getDrawable(block1 + rand));
                block.setXY(x * blockSize.width + margin, y * blockSize.height + margin);
                block.setWidth(blockSize.width);
                block.setHeight(blockSize.height);

                mListBlock.add(block);
                Log.d("hy", "initBlock: mListBlock : " + mListBlock.size());
            }
        }

        lives = 3;
    }

    int getRandom(int min, int max) {
        return Math.abs(mRandom.nextInt()) % (max - min + 1) + min;
    }

    public void run() {
        // TODO Auto-generated method stub
        Paint paint = new Paint();

        int[] ball_speed_x = new int[ball.length];
        int[] ball_speed_y = new int[ball.length];
        Point[] ball_pt = new Point[ball.length];

        SurfaceHolder holder = this.getHolder();
        Canvas canvas;
        Bitmap bitmap;

        Rect src, dest;

        for (int i = 0; i != ball.length; ++i) {
            ball_speed_x[i] = MAX_SPEED;
            ball_speed_y[i] = MAX_SPEED;
            ball_pt[i] = new Point(
                    m_rectFrame.centerX(),
                    m_rectFrame.centerY());
        }
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dest = new Rect(0, 0, getWidth(), getHeight());

        paint.setAlpha(255);

        while (thread != null) {

            canvas = holder.lockCanvas();

//            paint = new Paint();
//            canvas.drawColor(Color.BLACK);//the background
//            paint.setColor(Color.argb(255, 255, 255, 255));
//            paint.setTextSize(45);
//            canvas.drawText("Score:" + score + " Lives:" + lives, 20, 40, paint);

            canvas.drawBitmap(bitmap, src, dest, null);
            for (int i = 0; i != ball.length; ++i) {
                canvas.drawBitmap(ball[i], ball_pt[i].x, ball_pt[i].y, paint);
            }

            for (Block block : this.mListBlock)
                block.draw(canvas); // 블록 으로 파라미터로 캔버스를 넘겨주고 사각형 그려주기

            paint = new Paint();
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(45);
            canvas.drawText("Score:" + score + " Lives:" + lives, 20, 40, paint);

            holder.unlockCanvasAndPost(canvas);


            for (int i = 0; i != ball.length; ++i) {
                // 벽을 쳤을때 ~~ 오른쪽 왼쪽 제한두기
                if ((ball_pt[i].x < 0) || ((ball_pt[i].x + ballWidth) > getWidth())) {
                    ball_speed_x[i] = -ball_speed_x[i];

                }
                // 윗 벽을 쳤을때 제한두깅
                if (ball_pt[i].y < 0) {
                    ball_speed_y[i] = -ball_speed_y[i];

                }
                if ((ball_pt[i].y + ballHeight) > getHeight()) {
                    lives = lives - 1;

                    if(lives == 0) {
                        lives = 3;
                        score = 0;
                    }
                  //  initBlock();
                    ball_pt[0] = new Point(
                            m_rectFrame.centerX(),
                            m_rectFrame.centerY());
                }
                Block blockRemove = null;
                for (int j = 0; j < mListBlock.size(); j++) {
                    Block block = mListBlock.get(j);
                    if (block.isCollision(ball_pt[i])) {
                        if (j > 0) blockRemove = block;
//                        Drawable temp = block.getDrawable();
//                        Drawable temp1 = block1
////                        ArrayList<Block> listblock = new ArrayList<Block>();
////                        listblock.add(block);
////                        if(block.isCollision(ball_pt[i]))
////                        {
////
////                        }
                        Drawable temp = block.getDrawable();
                        Drawable temp1 = getResources().getDrawable(R.drawable.block1); // 빨
                        Drawable temp2 = getResources().getDrawable(R.drawable.block2); // 노
                        Drawable temp3 = getResources().getDrawable(R.drawable.block3); // 파
                        Drawable temp4 = getResources().getDrawable(R.drawable.block4); // 초

                        Bitmap tmpBitmap = ((BitmapDrawable)temp).getBitmap();
                        Bitmap tmpBitmap1 = ((BitmapDrawable)temp1).getBitmap();
                        Bitmap tmpBitmap2 = ((BitmapDrawable)temp2).getBitmap();
                        Bitmap tmpBitmap3 = ((BitmapDrawable)temp3).getBitmap();
                        Bitmap tmpBitmap4 = ((BitmapDrawable)temp4).getBitmap();



                        if(tmpBitmap.equals(tmpBitmap1)){
                        score += 3;
                    }

                        if(tmpBitmap.equals(tmpBitmap2)){
                            score += 2;
                        }

                        if(tmpBitmap.equals(tmpBitmap3)){
                            score += 4;
                        }


                        if(tmpBitmap.equals(tmpBitmap4)){
                            score += 5;
                        }

                        Log.d("hy", "색 구분" + mListBlock.get(i));
                        // 이 부분
                        //score++;

//                        if(i == 1) {
//                            score += 2;
//                        }
//
//                        else if(i == 2){
//                            score += 1;
//                        }
//
//                        else if(i == 3){
//                            score += 3;
//                        }
//                        else if(i == 1){
//                            score = scr
//                        }
//                        if(i = BitmapFactory.decodeResource(mListBlock.get(i).getResources(), R.drawable.block1)))
//                            else if(i == 파랑)
                        //score++;
                        if (block.isCollisionVertical(ball_pt[i]))
                            ball_speed_y[i] = -ball_speed_y[i];
                        else
                            ball_speed_x[i] = -ball_speed_x[i];
                    }
                }
                this.mListBlock.remove(blockRemove);

                ball_pt[i].x += ball_speed_x[i];
                ball_pt[i].y += ball_speed_y[i];

                if (mListBlock.size() <= 1) {
                    initBlock();
                    ball_pt[0] = new Point(m_rectFrame.centerX(), m_rectFrame.centerY());
                }
            }
        }
    }

    // 화면(Surface) 사이즈가 변경되면 자동으로 호출되는 콜백 메서드
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        //
    }

    // Surface가 생성될때 자동으로 호출되눈 콜백 메서드
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        thread = new Thread(this);
        thread.start(); // 여기서 애니메이션 시작

    }

    // Surface가 소멸될때 자동으로 호출되눈 콜백 메서드
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        thread = null; // 여기는 애미메니션 중단 즉, 쓰레드 중단
    }


    private Bitmap transparentBitmap(Bitmap bitmap, int w, int h) {
        int[] pixels = new int[ballWidth * ballHeight];
        bitmap.getPixels(pixels, 0, ballWidth, 0, 0, ballWidth, ballHeight);

        for (int i = 0; i != pixels.length; ++i) {
            if (pixels[i] == Color.WHITE) {
                pixels[i] = Color.TRANSPARENT;
            }
        }

        return Bitmap.createBitmap(pixels, 0, ballWidth, ballWidth, ballHeight,
                Bitmap.Config.ARGB_8888);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Block block = mListBlock.get(0);

        float tempX = event.getX() - 250;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (tempX + 500 >= 1080) {
                    Log.d("hy", "onTouchEvent: eventX : " + event.getX());
                    return true;
                }

                block.setX((int) tempX);
                block.setSize(600, 70);
        }
        return true;
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }
}
