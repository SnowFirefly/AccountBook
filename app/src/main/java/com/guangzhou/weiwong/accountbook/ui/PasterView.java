package com.guangzhou.weiwong.accountbook.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Tower on 2016/4/28.
 */
public class PasterView extends TextView{
    private final String TAG = getClass().getName();
    private Paint mPaint;
    private boolean sNeedDraw = false;
    private String content = "";
    private int mHeight, mWidth, mPadL, mPadR, mPadT, mPadB;
    private Typeface typeface;

    public PasterView(Context context) {
        super(context);
//        init();
    }

    public PasterView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public PasterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
//        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fangzheng_jinglei.ttf"));
        this.typeface = tf;
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getCurrentTextColor());
        mPaint.setTextSize(35);
        if (typeface != null)
            mPaint.setTypeface(typeface);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mHeight = getHeight();
        mWidth = getWidth();
        mPadT = mHeight / 3;
        mPadL = mWidth / 4;
        mPadR = mWidth / 4;
        Log.d(TAG, "mHeight = " + mHeight + ", mWidth = " + mWidth + ", mPadT = " + mPadT + ", mPadL = " + mPadL);
        Log.d(TAG, "hashCode: " + hashCode());
        String[] texts = content.split("\n");
        if (sNeedDraw) {
            init();
            for (int i=0; i<texts.length; i++) {
                canvas.drawText(texts[i], mPadL, mPadT + i*50, mPaint);
            }
        } else {
            sNeedDraw = true;
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (type == BufferType.SPANNABLE){

        }
    }

    public void setContent(String text){
        content = text;
        invalidate();
    }

    public void setContent(String text, Typeface typeface){
        content = text;
        if (typeface != null) {
            this.typeface = typeface;
        }
        invalidate();
    }
}
