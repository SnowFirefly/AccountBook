package com.guangzhou.weiwong.accountbook.mvp.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.MyApplication;
import com.guangzhou.weiwong.accountbook.utils.SpUtil;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.wong.greendao.DBHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ShimmerTextView shimmerTextView = (ShimmerTextView) findViewById(R.id.tv_shimmer);
        final Shimmer shimmer = new Shimmer();
        shimmer.start(shimmerTextView);
        /*mShimmer.setRepeatCount(0)
                .setDuration(500)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL)
                .setAnimatorListener(new Animator.AnimatorListener() {
                });*/

        DBHelper.init(this);    // 此处初始化数据库操作类

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                if (TextUtils.isEmpty(SpUtil.getStringPreference(SplashActivity.this, SpUtil.KEY_USER_EMAIL, ""))) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                shimmer.cancel();
                finish();
                Log.i("SplashActivity", "startActivity");
            }
        }, 2000);
        new Handler((Looper.getMainLooper())).postDelayed(new Runnable() {
            @Override
            public void run() {
                MyApplication.setTypeface(Typeface.createFromAsset(SplashActivity.this.getAssets(), "fangzheng_jinglei.ttf"));
            }
        }, 1000);
    }
}
