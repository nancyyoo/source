package com.enjoy.mygravityball;

/**
 * Created by yooheeyoung on 2017. 11. 8..
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameView extends View {

    private Context m_Context;
    private GameThread m_Thread;

    private Bitmap bmpBack;
    private int w, h;

    private List<Ball> mBallArray;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_Context = context;
        BallResources.set(context);

        // 동기화시킬 수 있게 미리처리
        mBallArray = Collections.synchronizedList(new ArrayList<Ball>());


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    // 스크린사이즈 담아주고
        this.w = w;
        this.h = h;

        // 화면 크기가 바뀌니까 화면에 따라 조정해주는거임 보통!
        bmpBack = BitmapFactory.decodeResource(getResources(), R.drawable.field);
        bmpBack = Bitmap.createScaledBitmap(bmpBack, w, h, true);

        // 공들이 업데이트 !
        if(m_Thread == null)
        {
            m_Thread = new GameThread();
            m_Thread.start();
        }



    }

    //-----------------------------
    // View의 종료
    //-----------------------------
    @Override
    protected void onDetachedFromWindow() {

        m_Thread.bRun = false; // 종료 시키는거
        super.onDetachedFromWindow();
    }



    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(bmpBack, 0, 0, null);

        synchronized (mBallArray) {
            // 리스트에 있는 공을 모두 그려줘야하니까
            for (Ball ball : mBallArray) {
                // 액세스 하고 있는 데 중간에 누가 지워줄 수 있음
                // ondraw에 싱크로나이즈하면 느려짐!
                canvas.rotate(ball.angle, ball.x, ball.y);
                canvas.drawBitmap(ball.bmpBall, ball.x - ball.r, ball.y - ball.r, null);

            }
        }
    }




    private synchronized void makeBall(float x, float y) {
        mBallArray.add( new Ball(w, h, x, y) );
    }



    private synchronized void moveBall() {
        for(Ball ball : mBallArray) {
            ball.update();
        }
    }



    private synchronized void removeDead() {
        for (int i = mBallArray.size() - 1; i >= 0; i--) {
            if (mBallArray.get(i).isDead) {
                mBallArray.remove(i);
            }
        }
    }


    //-----------------------------
    // Touch Event
    //-----------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            // 터치햇을 대 공 생성
            makeBall(event.getX(), event.getY());

        }

        return true;
    }

    class GameThread extends Thread{
        public boolean bRun = true;

        public void run(){

            while(bRun){

                try {
                    Time.update();

                    moveBall();
                    removeDead();
                    postInvalidate();
                    sleep(10);
                }
                catch (Exception e){

                }

            }
        }

    }
} // GameView
