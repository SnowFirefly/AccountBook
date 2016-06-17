package com.guangzhou.weiwong.accountbook.mvp.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerRegisterPresenterComponent;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IRegisterPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.RegisterPresenter;
import com.guangzhou.weiwong.accountbook.utils.AnimUtil;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseMvpActivity implements IView {
    private final String TAG = "RegisterActivity";
    @Inject IRegisterPresenter iRegisterPresenter;

    @Bind(R.id.fab_circle) FloatingActionButton mFabCircle;
    @Bind(R.id.ll_info)
    LinearLayout mLlInfo;
    @Bind(R.id.iv_close)
    ImageView mIvClose;
    @Bind(R.id.rl_container)
    RelativeLayout mRlContainer;

    @Bind(R.id.et_user) TextInputEditText mEtUser;
    @Bind(R.id.et_email) TextInputEditText mEtEmail;
    @Bind(R.id.et_pw) TextInputEditText mEtPw;
    @Bind(R.id.et_pw_confirm) TextInputEditText mEtConfirmPw;
    @Bind(R.id.iv_visibility) ImageView mIvVisibility;
    @Bind(R.id.til_user) TextInputLayout mTilUser;
    @Bind(R.id.til_email) TextInputLayout mTilEmail;
    @Bind(R.id.til_pw) TextInputLayout mTilPw;
    @Bind(R.id.til_pw_confirm) TextInputLayout mTilConfirmPw;

    String user, email, pw, confirmPw;
    private boolean isVisibility = false;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterPresenterComponent.builder()
                .build().inject(this);
        if (iRegisterPresenter != null) {
            iRegisterPresenter.onAttach(this);
        }
    }

    private CircularProgressButton circularBtn;
    private void showProgressBtn(){
        circularBtn = (CircularProgressButton) findViewById(R.id.cpb_register);
        circularBtn.setIndeterminateProgressMode(true);
        circularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularBtn.getProgress() == 0) {
                    if (checkFormat()) {
                        circularBtn.setProgress(50);
                        onRegister(v);
                    }
                } else if (circularBtn.getProgress() == 100) {   // -1
                    circularBtn.setProgress(0);
                } else if (circularBtn.getProgress() == -1){
                    circularBtn.setProgress(0);
                }
            }
        });
    }

    public void onRegister(View view){
        iRegisterPresenter.register(user, email, pw);
    }

    private boolean checkFormat(){
        user = mEtUser.getText().toString();
        email = mEtEmail.getText().toString();
        pw = mEtPw.getText().toString();
        confirmPw = mEtConfirmPw.getText().toString();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(pw) || TextUtils.isEmpty(confirmPw)) {
            if (TextUtils.isEmpty(user)) {mEtUser.setError("姓名不能为空");}
            if (TextUtils.isEmpty(email)) {mEtEmail.setError("邮箱不能为空");}
            if (TextUtils.isEmpty(pw)) {mEtPw.setError("密码不能为空");}
            if (TextUtils.isEmpty(confirmPw)) {mEtConfirmPw.setError("请再次输入密码");}
            return false;
        }
        if (!confirmPw.equals(pw)) {
            mEtConfirmPw.setError("两次输入的密码不一致");
            return false;
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        MyLog.d(TAG, "onCreate");
        iRegisterPresenter.onAttach(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation(); // 入场动画
            setupExitAnimation(); // 退场动画
        } else {
            initViews();
        }
        MyLog.d(TAG, "onCreate done");
        showProgressBtn();
    }

    // 初始化视图
    private void initViews() {
        Log.d(TAG, "initViews");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(RegisterActivity.this, android.R.anim.fade_in);
                animation.setDuration(300);
                mLlInfo.startAnimation(animation);
                mIvClose.setAnimation(animation);
                mLlInfo.setVisibility(View.VISIBLE);
                mIvClose.setVisibility(View.VISIBLE);

                mFabCircle.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Visible");
                mEtUser.addTextChangedListener(new MyTextWatcher(mTilUser));
                mEtEmail.addTextChangedListener(new MyTextWatcher(mTilEmail));
                mEtPw.addTextChangedListener(new MyTextWatcher(mTilPw));
                mEtConfirmPw.addTextChangedListener(new MyTextWatcher(mTilConfirmPw));
                mIvVisibility.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isVisibility) {
                            mEtPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            mEtConfirmPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            mIvVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_red_eye_white_24dp));
                        } else {
                            mEtPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            mEtConfirmPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            mIvVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_red_eye_grey600_24dp));
                        }
                        isVisibility = !isVisibility;
                    }
                });
            }
        });
    }

    private class MyTextWatcher implements TextWatcher {
        private TextInputLayout textInputLayout;
        public MyTextWatcher(View view) {
            if (view instanceof TextInputLayout) {
                textInputLayout = (TextInputLayout) view;
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) textInputLayout.setErrorEnabled(false);
        }
        @Override
        public void afterTextChanged(Editable s) {}
    }

    // 入场动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.d(TAG, "onTransitionStart");
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {
                Log.d(TAG, "onTransitionCancel");
            }

            @Override
            public void onTransitionPause(Transition transition) {
                Log.d(TAG, "onTransitionPause");
            }

            @Override
            public void onTransitionResume(Transition transition) {
                Log.d(TAG, "onTransitionResume");
            }
        });
        getWindow().setSharedElementEnterTransition(transition);

    }

    // 退出动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(300);
    }

    // 动画展示
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        Log.d(TAG, "animateRevealShow");
        AnimUtil.animateRevealShow(
                this, mRlContainer,
                mFabCircle.getWidth() / 2, R.color.colorAccent,
                new AnimUtil.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        initViews();
                    }
                });
    }

    // 退出按钮
    public void backActivity(View view) {
        mFabCircle.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            onBackPressed();
        } else {
            defaultBackPressed();
        }
    }

    // 退出事件
    @Override public void onBackPressed() {
        mFabCircle.setVisibility(View.VISIBLE);
        AnimUtil.animateRevealHide(
                this, mRlContainer,
                mFabCircle.getWidth() / 2, R.color.colorAccent,
                new AnimUtil.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                        defaultBackPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    // 默认回退
    private void defaultBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSignResult(String resultMsg) {
        Toast.makeText(this, resultMsg, Toast.LENGTH_LONG).show();
        circularBtn.setProgress(100);
    }

    @Override
    public void onError(String errorMsg) {
        Snackbar.make(mFabCircle, errorMsg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        circularBtn.setProgress(-1);
    }

    @Override
    public <T> void onLoadResult(int type, T bean) {

    }

    @Override
    public void onCommitResult(String resultMsg) {

    }
}
