package com.enjoy.myblockgame;

/**
 * Created by yooheeyoung on 2017. 9. 25..
 */

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

// 블럭 클래스
public class Block {

    Rect m_rect = new Rect(); // 사각형 만들어주고
    Drawable m_drawable; // 이미지를 가져오기 위해 선언

    public void setXY(int x, int y) { // x,y 좌표 설정
        setX(x);
        setY(y);
    }

    public void setSize(int w, int h) { // 사이즈 설정
        setWidth(w);
        setHeight(h);
    }
    // 블록 설정!! 사각 모양이나 그림 이미지로
    // get, set으로 받아오고 설정하는 것   을 깨묜 바로 그려짐 한곳에서그려짐
    public int getX() {
        return m_rect.left;
    }

    public void setX(int x) {
        if (x > 0) m_rect.left = x;
    }

    public int getY() {
        return m_rect.top;
    }

    public void setY(int y) {
        if (y > 0) m_rect.top = y;
    }

    public int getWidth() {
        return m_rect.width();
    }

    public void setWidth(int w) {
        if (w > 0) m_rect.right = m_rect.left + w;
    }

    public int getHeight() {
        return m_rect.height();
    }

    public void setHeight(int h) {
        if (h > 0) m_rect.bottom = m_rect.top + h;
    }

    public void setDrawable(Drawable drawable) {
        m_drawable = drawable;
    } // 블록 이미지 등록

    public Drawable getDrawable() {
        return m_drawable;
    }

    public boolean isCollision(Point pt) {  // 충돌 체크
        if (m_rect.contains(pt.x, pt.y)) return true;
        return false;
    }

    public void moveLeft(int amount) {
        m_rect.left -= amount;
        m_rect.right -= amount;
    }

    public void moveRight(int amount) {
        m_rect.left += amount;
        m_rect.right += amount;
    }

    public void draw(Canvas canvas) {
        this.m_drawable.setBounds(m_rect); // 이미지 시작위치 잡아주고
        this.m_drawable.draw(canvas); // 그려주기
    }
// 충돌처리
    public boolean isCollisionVertical(Point pt) {
        double distUp, distDown, distLeft, distRight;
        distUp = Math.abs(pt.y - m_rect.top);
        distDown = Math.abs(pt.y - m_rect.bottom);
        distLeft = Math.abs(pt.x - m_rect.left);
        distRight = Math.abs(pt.x - m_rect.right);

        double distVertical = Math.min(distUp, distDown);
        double distHorizontal = Math.min(distLeft, distRight);
        if (distVertical < distHorizontal) return true;

        return false;
    }

}
