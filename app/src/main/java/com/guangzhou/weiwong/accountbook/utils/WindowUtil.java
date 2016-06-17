package com.guangzhou.weiwong.accountbook.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.guangzhou.weiwong.accountbook.R;

/**
 * 弹窗辅助类（Loading数据）
 * Created by Tower on 2016/6/12.
 */
public class WindowUtil {
    private static final String TAG = "WindowUtil";
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;
    private static boolean isShown = false;

    public static void showPopupWindow(final Context context) {
        if (isShown) {
            MyLog.i(TAG, "return cause already shown");
            return;
        }
        isShown = true;
        MyLog.i(TAG, "showPopupWindow");
        mContext = context.getApplicationContext();
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mView = setUpView(mContext);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        mWindowManager.addView(mView, params);
        MyLog.i(TAG, "add view");
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        MyLog.i(TAG, "hide " + isShown + ", " + mView);
        if (isShown && null != mView) {
            MyLog.i(TAG, "hidePopupWindow");
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    private static View setUpView(final Context context) {
        MyLog.i(TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.test_window,
                null);

        createView(view);

        /*ImageView imageView = (ImageView) view.findViewById(R.id.iv_window);
        imageView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLog.i(TAG, "ok on click");

            }
        });*/

        // 点击窗口外部区域可消除
        // 这点的实现主要将悬浮窗设置为全屏大小，外层有个透明背景，中间一部分视为内容区域
        // 所以点击内容区域外部视为点击悬浮窗外部
        final View popupWindowView = view.findViewById(R.id.rl_window);// 非透明的内容区域
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MyLog.i(TAG, "onTouch");
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = new Rect();
                popupWindowView.getGlobalVisibleRect(rect);
                if (!rect.contains(x, y)) {
                    WindowUtil.hidePopupWindow();
                }
                MyLog.i(TAG, "onTouch : " + x + ", " + y + ", rect: "
                        + rect);
                return false;
            }
        });
        // 点击back键可消除       (tower: 无效)
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                MyLog.i(TAG, "onKey: keyCode = " + keyCode);
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        WindowUtil.hidePopupWindow();
                        return true;
                    default:
                        return false;
                }
            }
        });
        return view;
    }

    /**
     * 开始黑点的动画
     */
    private static class AnimHandler extends Handler {
        public AnimHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: startAnimation(pot0); break;
                case 1: startAnimation(pot1); break;
                case 2: startAnimation(pot2); break;
                case 3: startAnimation(pot3); break;
                case 4: startAnimation(pot4); break;
            }
        }
    }

    private static AnimHandler animHandler;
    private static ImageView pot0, pot1, pot2, pot3, pot4;
    private static void createView(View view) {
        pot0 = (ImageView) view.findViewById(R.id.pot);
        pot1 = (ImageView) view.findViewById(R.id.pot1);
        pot2 = (ImageView) view.findViewById(R.id.pot2);
        pot3 = (ImageView) view.findViewById(R.id.pot3);
        pot4 = (ImageView) view.findViewById(R.id.pot4);

        animHandler = new AnimHandler(Looper.getMainLooper());
        animHandler.sendEmptyMessageDelayed(0, 0);
        animHandler.sendEmptyMessageDelayed(1, delay);
        animHandler.sendEmptyMessageDelayed(2, delay*2);
        animHandler.sendEmptyMessageDelayed(3, delay*3);
        animHandler.sendEmptyMessageDelayed(4, delay*4);
    }

    private static int delay = 200;
    private static int duration = 1000;
    /**
     * 动画设置（减速——>匀速——>减速，对应inAnimation——>waitAnimation——>outAnimation）
     * @param view
     * @param fromXDelta
     * @param toXDelta
     * @param fromYDelta
     * @param toYDelta
     * @param time
     * @param repeatCount
     */
    private static void anim(final View view, float fromXDelta, final float toXDelta, final float fromYDelta, final float toYDelta, long time, int repeatCount) {
        AnimationSet animationSet = new AnimationSet(true);
        final LinearInterpolator linearInterpolator = new LinearInterpolator();
        final DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
        TranslateAnimation inAnimation = new TranslateAnimation(-20, toXDelta/3, fromYDelta, toYDelta);
//        inAnimation.setInterpolator(decelerateInterpolator);
        inAnimation.setDuration(duration / 2);

        inAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation waitAnimation = new TranslateAnimation(toXDelta/3, toXDelta*2 / 3, fromYDelta, toYDelta);
                waitAnimation.setInterpolator(linearInterpolator);
                waitAnimation.setDuration(duration + 500);
//                waitAnimation.setStartOffset(4000);
                waitAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        TranslateAnimation outAnimation = new TranslateAnimation(toXDelta * 2 / 3, toXDelta+20, fromYDelta, toYDelta);
                        outAnimation.setDuration(duration / 2);
                        outAnimation.setInterpolator(decelerateInterpolator);
                        outAnimation.setFillAfter(true);
                        view.clearAnimation();
                        view.startAnimation(outAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                view.clearAnimation();
                view.startAnimation(waitAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animationSet.addAnimation(inAnimation);
        // 使用AnimationSet时会令Animation的setInterpolator方法失效？（覆盖？）但不会影响未添加的Animation？
        animationSet.setInterpolator(decelerateInterpolator);
        view.startAnimation(animationSet);
    }

    /**
     * 嵌套循环播放动画
     * @param view
     */
    public static void startAnimation(final View view){
        DisplayMetrics dm = getDM(mContext);
        MyLog.i("window", "dm.width:" + dm.widthPixels * dm.density + ",dm.height:" + dm.heightPixels * dm.density);
        anim(view, 0, dm.widthPixels, 0, 0, 2000, 2);
        animHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isShown) {
                    startAnimation(view);
                }
            }
        }, 4000);
//		Log.i("window", "display.getWidth:"+display.getWidth()+",getHeight:"+display.getHeight());
//		anim(view, loc[0], display.getWidth()/2, loc[1], 0, 1000, 5);
//        int[] loc = new int[2];
//        view.getLocationOnScreen(loc);
//        anim(view, loc[0], dm.widthPixels*dm.density/2, loc[1], 0, 1000, 2);
    }

    public static DisplayMetrics getDM(Context context){
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm;
    }

    public static boolean isShown() {
        return isShown;
    }
}
