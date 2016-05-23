package com.guangzhou.weiwong.accountbook.mvp.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guangzhou.weiwong.accountbook.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ShimmerTextView shimmerTextView = (ShimmerTextView) findViewById(R.id.tv_shimmer);
        final Shimmer shimmer = new Shimmer();
        shimmer.start(shimmerTextView);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmer.cancel();
                finish();
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }, 2000);
    }
}