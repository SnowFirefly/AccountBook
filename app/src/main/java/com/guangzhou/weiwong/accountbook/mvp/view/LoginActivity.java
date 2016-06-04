package com.guangzhou.weiwong.accountbook.mvp.view;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerLoginPresenterComponent;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.model.Network;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.mvp.presenter.ILoginPresenter;
import com.guangzhou.weiwong.accountbook.utils.BlurUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseMvpActivity implements IView{
    private final String TAG = getClass().getName();
    @Bind(R.id.fab) FloatingActionButton mFab;
    @Bind(R.id.et_user) TextInputEditText mEtUser;
    @Bind(R.id.et_pw) TextInputEditText mEtPw;
    @Bind(R.id.ll_login) LinearLayout mLlLogin;

    @Inject
    Network network;
    @Inject ILoginPresenter iLoginPresenter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        super.setupActivityComponent(appComponent);
        DaggerLoginPresenterComponent.builder()
                .appComponent(appComponent)
//                .loginPresenterModule(new LoginPresenterModule(this))
                .build()
                .inject(this);
        iLoginPresenter.onAttach(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG, "onCreate: " + "before bind");
        ButterKnife.bind(this);
        Log.i(TAG, "onCreate: " + "after bind");
//        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(view);
            }
        });
        Log.i(TAG, "onCreate: " + "before get ActivityManager");
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        Log.i(TAG, "heapSize: " + heapSize + "MB");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showProgressBtn();
                animate();
            }
        }, 100);

        iLoginPresenter.testLogin("wong", "123");
        Log.i(TAG, "onCreate: " + "end");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLlLogin.setVisibility(View.VISIBLE);
    }

    public void login(View view){
        if (checkFormat(mEtUser.getText().toString(), mEtPw.getText().toString())) {
            iLoginPresenter.login(mEtUser.getText().toString(), mEtPw.getText().toString());
        } else {
            Snackbar.make(view, "Username and password cannot be null.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            startActivity(new Intent(this, MainActivity.class));

        }
        Log.d(TAG, "login()");
    }

    private boolean checkFormat(String userName, String password){
        if (userName == null || password == null || userName.equals("") || password.equals(""))
            return false;
        return true;
    }

    @Override
    public void onLoginResult(Result result) {
        Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterResult(Result result) {

    }

    // Fab的跳转事件
    public void startRegisterActivity(View view) {
        mLlLogin.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
            startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    private void showProgressBtn(){
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.cpb_login);
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    circularButton1.setProgress(50);
                } else if (circularButton1.getProgress() == 100) {   // -1
                    circularButton1.setProgress(0);
                } else {
                    circularButton1.setProgress(100);        // -1
                    circularButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            login(v);
                        }
                    });
                }

                /*if (circularButton1.getProgress() == 0) {
                    simulateErrorProgress(circularButton1);
                } else {
                    circularButton1.setProgress(0);
                }*/

                /*if (circularButton1.getProgress() == 0) {
                    circularButton1.setProgress(100);   // -1
                } else {
                    circularButton1.setProgress(0);
                }*/

            }
        });
    }

    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }

    private final int STARTUP_DELAY = 300, ANIM_ITEM_DURATION = 1000, ITEM_DELAY = 300;
    @Bind(R.id.iv_smile) ImageView mIvSmile;
    @Bind(R.id.btn_register) Button mBtnRegister;
    private void animate(){
        // 背景模糊（毛玻璃效果）
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_login);
        Bitmap newBitmap = BlurUtil.fastblur(this, bitmap, 12);
        RelativeLayout mRlLoginRoot = (RelativeLayout) findViewById(R.id.rl_login_root);
        mRlLoginRoot.setBackground(new BitmapDrawable(newBitmap));

        ViewCompat.animate(mIvSmile)
                .translationY(-300).alpha(1)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        for (int i = 0; i < mLlLogin.getChildCount(); i++) {
            View v = mLlLogin.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            // TextView控件, Button是TextView的子类

            // 从消失到显示
            if ((v instanceof LinearLayout)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
                viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
            } else if (v instanceof CircularProgressButton){ // Button控件, 从缩小到扩大
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
                viewAnimator.setInterpolator(new DecelerateInterpolator()).start();

                final Animation strickenAnim = AnimationUtils.loadAnimation(this, R.anim.stricken);
                strickenAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mBtnRegister.setVisibility(View.INVISIBLE);
                        mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_register));
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                strickenAnim.setFillEnabled(true);
                strickenAnim.setFillAfter(true);

                Animation animation = AnimationUtils.loadAnimation(this, R.anim.strike);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mBtnRegister.startAnimation(strickenAnim);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                animation.setStartOffset((ITEM_DELAY * i) + 500);
                animation.setFillEnabled(true);
                animation.setFillAfter(true);
                v.startAnimation(animation);
            } else if (v instanceof Button) { // Button控件, 从缩小到扩大
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
                viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
            }
        }
    }

}
