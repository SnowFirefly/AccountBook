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
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
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
import com.guangzhou.weiwong.accountbook.mvp.presenter.ILoginPresenter;
import com.guangzhou.weiwong.accountbook.utils.BlurUtil;
import com.guangzhou.weiwong.accountbook.utils.ImageUtil;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.SpUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseMvpActivity implements IView {
    private final String TAG = getClass().getName();
    @Bind(R.id.fab) FloatingActionButton mFab;
    @Bind(R.id.et_user) TextInputEditText mEtUser;
    @Bind(R.id.et_pw) TextInputEditText mEtPw;
    @Bind(R.id.ll_login) LinearLayout mLlLogin;
    @Bind(R.id.til_user) TextInputLayout mTilUser;
    @Bind(R.id.til_pw) TextInputLayout mTilPw;
    @Bind(R.id.iv_visibility) ImageView mIvVisibility;
    private boolean isVisibility = false;
    private String userName, email, password;

    @Inject
    Network network;
    @Inject
    ILoginPresenter iLoginPresenter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
//        super.setupActivityComponent(appComponent);
        DaggerLoginPresenterComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        if (iLoginPresenter != null)
        iLoginPresenter.onAttach(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyLog.i(TAG, "onCreate: " + "before bind");
        ButterKnife.bind(this);
        MyLog.i(TAG, "onCreate: " + "after bind");
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(view);
            }
        });
        MyLog.i(TAG, "onCreate: " + "before get ActivityManager");
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        MyLog.i(TAG, "heapSize: " + heapSize + "MB");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showProgressBtn();
                animate();
            }
        }, 100);

        init();
        setupListener();
        MyLog.i(TAG, "onCreate: " + "end");
    }

    private void init() {
        userName = SpUtil.getStringPreference(this, SpUtil.KEY_USER_NAME, "");
        email = SpUtil.getStringPreference(this, SpUtil.KEY_USER_EMAIL, "");
        password = SpUtil.getStringPreference(this, SpUtil.KEY_USER_PASSWORD, "");
        mEtUser.setText(email);
        mEtPw.setText(password);
    }

    private void setupListener() {
        mEtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {mTilUser.setErrorEnabled(false);}
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) mTilPw.setErrorEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mIvVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisibility) {
                    mEtPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIvVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off_grey600_24dp));
                } else {
                    mEtPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIvVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_grey600_24dp));
                }
                isVisibility = !isVisibility;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLlLogin.setVisibility(View.VISIBLE);
        init();
    }

    public void login(View view){
        iLoginPresenter.login(mEtUser.getText().toString(), mEtPw.getText().toString());
        if (checkFormat(mEtUser.getText().toString(), mEtPw.getText().toString())) {
        } else {

        }
        Log.d(TAG, "login()");
    }

    private boolean checkFormat(String userName, String password){
//        startActivity(new Intent(this, MainActivity.class));
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(userName)) {
                mTilUser.setError("邮箱不能为空");
            }
            if (TextUtils.isEmpty(password)) {
                mTilPw.setError("密码不能为空");
            }
            return false;
        }/* else if (!validateEmail(userName)) {
            mTilUser.setError("邮箱格式错误");
            return false;
        } else if (!validatePassword(password)) {
            mTilPw.setError("密码长度至少为6个字符");
            return false;
        }*/
        email = userName;
        this.password = password;
        mTilUser.setErrorEnabled(false);
        mTilPw.setErrorEnabled(false);
        return true;
    }

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onSignResult(String resultMsg) {
        Toast.makeText(this, resultMsg, Toast.LENGTH_LONG).show();
        circularBtn.setProgress(100);
        SpUtil.putStringPreference(this, SpUtil.KEY_USER_EMAIL, email);
        SpUtil.putStringPreference(this, SpUtil.KEY_USER_PASSWORD, password);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
        }, 500);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 700);
    }

    @Override
    public void onError(String errorMsg) {
        circularBtn.setProgress(-1);
        Snackbar.make(mFab, errorMsg, Snackbar.LENGTH_LONG)      // "Username and password cannot be null."
                .setAction("Action", null).show();
    }

    @Override
    public <T> void onLoadResult(int type, T bean) {

    }

    @Override
    public void onCommitResult(String resultMsg) {

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

    private CircularProgressButton circularBtn;
    private void showProgressBtn(){
        circularBtn = (CircularProgressButton) findViewById(R.id.cpb_login);
        circularBtn.setIndeterminateProgressMode(true);
        circularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularBtn.getProgress() == 0) {
                    if (checkFormat(mEtUser.getText().toString(), mEtPw.getText().toString())) {
                        circularBtn.setProgress(50);
                        login(v);
                    }
                } else if (circularBtn.getProgress() == 100) {   // -1
                    circularBtn.setProgress(0);
                } else if (circularBtn.getProgress() == -1){
                    circularBtn.setProgress(0);
                }

                /*if (circularButton1.getProgress() == 0) {
                    simulateErrorProgress(circularButton1);
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
        MyLog.i(this, "300 dp2px: " + ImageUtil.dp2px(this, 300));
        MyLog.i(this, "300 px2dp: " + ImageUtil.px2dp(this, 300));

        ViewCompat.animate(mIvSmile)
                .translationY(-ImageUtil.dp2px(LoginActivity.this, 150f)).alpha(1)
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
//                        mFab.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake));
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
