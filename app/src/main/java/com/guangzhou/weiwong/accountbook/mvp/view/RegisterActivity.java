package com.guangzhou.weiwong.accountbook.mvp.view;

import android.annotation.TargetApi;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IRegisterPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.RegisterPresenter;
import com.guangzhou.weiwong.accountbook.utils.AnimUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity implements IView{
    private final String TAG = "RegisterActivity";
    private IRegisterPresenter iRegisterPresenter;

    @Bind(R.id.fab_circle)
    FloatingActionButton mFabCircle;
    @Bind(R.id.ll_info)
    LinearLayout mLlInfo;
    @Bind(R.id.iv_close)
    ImageView mIvClose;
    @Bind(R.id.rl_container)
    RelativeLayout mRlContainer;

    @Bind(R.id.et_user) TextInputEditText mEtUser;
    @Bind(R.id.et_email) TextInputEditText mEtEmail;
    @Bind(R.id.et_pw) TextInputEditText mEtPw;
     EditText mEtConfirmPw;

    String user, email, pw, confirmPw;

    private void showProgressBtn(){
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.cpb_register);
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

    public void onRegister(View view){
        user = mEtUser.getText().toString();
        email = mEtEmail.getText().toString();
        pw = mEtPw.getText().toString();
//        confirmPw = mEtConfirmPw.getText().toString();
        if (checkFormat()){
            iRegisterPresenter.register(user, email, pw);
        } else {
            Snackbar.make(view, "Username, email and password cannot be null.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private boolean checkFormat(){
        if (user == null || email == null || pw == null || confirmPw == null
                || user.equals("") || email.equals("") || pw.equals("") || confirmPw.equals(""))
            return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate");
        iRegisterPresenter = createPresenter();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation(); // 入场动画
            setupExitAnimation(); // 退场动画
        } else {
            initViews();
        }
        Log.d(TAG, "onCreate done");
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
            }
        });
        /*new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
//                mLlInfo.setVisibility(View.VISIBLE);
            }
        }, 500);*/
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
    protected IRegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void onLoginResult(User user) {

    }

    @Override
    public void onRegisterResult(RegisterResult user) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();
    }
}
