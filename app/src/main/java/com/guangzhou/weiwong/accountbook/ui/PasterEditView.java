package com.guangzhou.weiwong.accountbook.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Tower on 2016/5/4.
 */
public class PasterEditView extends EditText {
    private final String TAG = getClass().getName();
    private int mLineColor;
    private float mLineWidth;

    private Paint mPaint;
    private int mPadL, mPadR, mPadT,    // 框内左右顶部留白
            mLines;      // 行数
    private float mSize,    // 字体大小
            mBaseTop,       // 第一条线位置
            mGap;    // 行宽

    public PasterEditView(Context context) {
        super(context);
        init();
    }

    public PasterEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasterEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PasterEditView(Context context, int mLineColor, float mLineWidth) {
        super(context);
        init();
        this.mLineColor = mLineColor;
        this.mLineWidth = mLineWidth;
    }

    private void init(){
        mLineColor = Color.BLUE;
        mLineWidth = 1.0f;
        mPaint = new Paint();
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mLineColor);

        mPadL = getPaddingLeft();
        mPadR = getPaddingRight();
        mPadT = getPaddingTop();
        mLines = getMaxLines();
        mSize = getTextSize();
        mBaseTop = mPadT;// + mSize / 6;
//        mGap = getLineHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mGap = getHeight() / mLines;
        Log.d(TAG, "mPadL = " + mPadL);
        Log.d(TAG, "mPadR = " + mPadR);
        Log.d(TAG, "mPadT = " + mPadT);
        Log.d(TAG, "getLineHeight = " + getLineHeight());
        Log.d(TAG, "mGap = " + mGap);
        Log.d(TAG, "mBaseTop = " + mBaseTop);
        Log.d(TAG, "getWidth = " + getWidth());
        Log.d(TAG, "mLines = " + mLines);
        for (int i = 0; i <= mLines; i++) {
            canvas.drawLine(mPadL,
                    mBaseTop + mGap * i,
                    getWidth() - mPadR,
                    mBaseTop + mGap * i,
                    mPaint);
        }
        super.onDraw(canvas);
    }

    public int getmLineColor() {
        return mLineColor;
    }

    public void setmLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public float getmLineWidth() {
        return mLineWidth;
    }

    public void setmLineWidth(float mLineWidth) {
        this.mLineWidth = mLineWidth;
    }
}
