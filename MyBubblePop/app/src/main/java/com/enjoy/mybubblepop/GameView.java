package com.enjoy.mybubblepop;

/**
 * Created by yooheeyoung on 2017. 11. 10..
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.enjoy.mybubblepop.R;

import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View {

    private Context m_context;
    private Bitmap m_bmpBack;
    private int m_w, m_h;

    // for bubble
    private ArrayList<Bubble> bubbleArray = new ArrayList<Bubble>();
    static public ArrayList<SmallBubble> smallBubbleArray = new ArrayList<SmallBubble>();
    private Paint m_paint = new Paint();
    private Random rnd = new Random();

/*
    Handler m_handler = new Handler() {
        public void handleMessage(Message msg) {
            makeBubble();
            moveBubbles();
            removeBubbles();
            invalidate();
            m_handler.sendEmptyMessageDelayed(0, 100);
        }
    };
*/

    Handler m_handler  = new Handler(new IncomingHandlerCallback());

    class IncomingHandlerCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            Time.update();
            makeBubble();
            moveBubbles();
            removeBubbles();
            invalidate();
            m_handler.sendEmptyMessageDelayed(0, 100);
            return true;
        }
    }


    public GameView(Context context) {
        super(context);
        this.m_context = context;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(m_bmpBack, 0, 0, null);

        for (Bubble bub : bubbleArray) {
            canvas.drawBitmap(bub.bmpBubble, bub.x - bub.r, bub.y - bub.r, null);
        }

        for (SmallBubble sbub : smallBubbleArray) {
            m_paint.setAlpha(sbub.alpha);
            canvas.drawBitmap(sbub.bmpBubble, sbub.x - sbub.r, sbub.y - sbub.r, m_paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        m_w = w;
        m_h = h;

        // ¹è°æ ÀÌ¹ÌÁö
        m_bmpBack = BitmapFactory.decodeResource(getResources(), R.drawable.sky);
        m_bmpBack = Bitmap.createScaledBitmap(m_bmpBack, w, h, true);

        m_handler.sendEmptyMessageDelayed(0, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            hitBubbleCheck(x, y);
        }

        return true;
    }

    private void makeBubble() {
        if (bubbleArray.size() < 20 && rnd.nextInt(1000) < 8) {
            bubbleArray.add( new Bubble(m_context, m_w, m_h, x, y) );
        }
    }

    private void moveBubbles() {
        for (Bubble bub : bubbleArray) {
            bub.update();
        }

        for (SmallBubble sbub : smallBubbleArray) {
            sbub.update();
        }


    }

    private void removeBubbles() {
        for (int i = bubbleArray.size() - 1; i >= 0; i--) {
            if (bubbleArray.get(i).isTouched) {
                bubbleArray.remove(i);
            }
        }

        for (int i = smallBubbleArray.size() - 1; i >= 0; i--) {
            if (smallBubbleArray.get(i).isDone) {
                smallBubbleArray.remove(i);
            }
        }
    }

    private void hitBubbleCheck(float x, float y) {

        for (Bubble bub : bubbleArray) {
            if (bub.hitCheck(x, y)) {
                break;
            }
        }
    }
}

