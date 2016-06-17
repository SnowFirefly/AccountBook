package com.guangzhou.weiwong.accountbook.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

/**
 * Created by Tower on 2016/6/12.
 */
public class LoadingSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private LoadingThread loadingThread;
    private boolean isDraw = false;
    private float screenWidth = 720, screenHeight = 1280; // 屏幕宽高
    private float picX = 0, picY = 50 / 2;      // 绘制所需的坐标
    private float conWidth = screenWidth / 5,           // 中间匀速的距离
            accWidth = (screenWidth - conWidth) / 2,    // 开头加速的距离
            decWith = accWidth;                         // 结尾减速的距离
    private int loadingTime = 3000, conTime = 1200,     // 单次loading总时间，中间匀速的时间
            accTime = (loadingTime - conTime) / 2,      // 开头加速的时间
            sleepTime = 20;                             // 重绘间隔时间
    private float minV, maxV;       // 最低/高速度
    private float a;                // 加速度（绝对值）
    private int t = 0;                  // 当前时间
    private float[] pXs;            // 每次绘制所需的横坐标
    private Bitmap bitmap;
    private Paint paint;

    private void init(Context context) {
        holder = getHolder();
        holder.addCallback(this);
        loadingThread = new LoadingThread();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pot1);
        paint = new Paint();
        pXs = calculate(loadingTime, sleepTime);
    }

    public LoadingSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public LoadingSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private float[] calculate(int loadingTime, int sleepTime) {
        int n = loadingTime / sleepTime;
        float[] pXs = new float[n];
        int t = 0;                         // 毫秒
        minV = conWidth / conTime;
        maxV = 2 * accWidth / accTime - minV;
        a = (maxV - minV) / accTime;
        MyLog.i(this, "minV:" + minV + ", maxV:" + maxV + ", a:" + a);
        for (int i = 0; i < pXs.length; i++) {
            t = sleepTime * i;
            if (t < accTime){
                MyLog.i(this, "t <= accTime");
                pXs[i] = maxV * t - a * t * t / 2;
            } else if (accTime <= t && t <= (accTime + conTime)) {
                MyLog.i(this, "accTime < t && t <= (accTime + conTime)");
                pXs[i] = accWidth + (t - accTime) * minV;
            } else { // if ((accTime + conTime) < t && t <= loadTime) {
                MyLog.i(this, "(accTime + conTime) < t && t <= loadTime");
                pXs[i] = accWidth + conWidth + minV * (t - accTime - conTime)
                        + a * (t - accTime - conTime) * (t - accTime - conTime) / 2;
            }
            MyLog.i(this, "pXs[" + i + "] = " + pXs[i]);
        }
        return pXs;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        MyLog.d(this, "surfaceCreated");
        isDraw = true;
        loadingThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        MyLog.d(this, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        MyLog.d(this, "surfaceDestroyed");
        isDraw = false;
    }

    private class LoadingThread extends Thread {
        @Override
        public void run() {
            while (isDraw) {
                drawUI();
            }
            super.run();
        }
    }

    public void drawUI() {
        MyLog.d(this, "drawUI");
        Canvas canvas = holder.lockCanvas();
        try {
            picX = pXs[t++];
            drawCanvas(canvas);
            if (t >= pXs.length) {
                isDraw = false;
            }
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawCanvas(Canvas canvas) {
        paint.setColor(Color.WHITE);        //设置画笔为
        canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
        MyLog.i(this, "screenWidth:" + screenWidth + ", screenHeight:" + screenHeight);
//        canvas.drawColor(0x77727272);
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap, picX, picY, paint);
        MyLog.i(this, "picX:" +  picX + ", picY:" + picY);
    }
}
