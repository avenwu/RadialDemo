package com.avenwu.radialdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author chaobin
 * @date 1/10/14.
 */
public class CircleView extends View {
    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private Paint mPaint;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRadius = getContext().getResources().getDisplayMetrics().density * 100;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(getContext().getResources().getDisplayMetrics().density * 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec) / 2;
        int height = MeasureSpec.getSize(heightMeasureSpec) / 2;
        Log.d("CircleView", "onMeasure, height=" + height + ", width=" + width);
        updateCenter(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("CircleView", "onSizeChanged: w=" + w + ", h=" + h);
        updateCenter(w, h);
    }

    void updateCenter(int x, int y) {
        mCenterX = x / 2;
        mCenterY = y / 2;
    }

    boolean isInside(float x, float y) {
        return Math.pow(x - mCenterX, 2) + Math.pow(y - mCenterY, 2)
                <= Math.pow(mCenterX - getPaddingLeft(), 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isInside(event.getX(), event.getY())) {
                    mPaint.setColor(Color.MAGENTA);
                }
                break;
            case MotionEvent.ACTION_UP:
                mPaint.setColor(Color.RED);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isInside(event.getX(), event.getY())) {
                    mPaint.setColor(Color.RED);
                }
                Log.d("CircleView", "position, x=" + event.getX() + ", y=" + event.getY());
                break;
        }
        invalidate();
        return true;
    }

}
