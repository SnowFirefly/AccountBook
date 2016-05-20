package com.guangzhou.weiwong.accountbook.mvp.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.model.NetworkApiService;
import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;
import com.guangzhou.weiwong.accountbook.mvp.presenter.ILoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseMvpActivity implements IView{
    private final String TAG = getClass().getName();
    private FloatingActionButton mFab;
    @Bind(R.id.et_user) EditText mEtUser;
    @Bind(R.id.et_pw) EditText mEtPw;
    @Bind(R.id.ll_login) LinearLayout mLlLogin;

    @Bind(R.id.tv_shimmer) ShimmerTextView mShimmerTv;
    private Shimmer mShimmer;

    @Inject
    NetworkApiService networkApiService;
    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        DaggerActivityComponent.builder().build().injectActivity(this);
        networkApiService = new NetworkApiService();
        loginPresenter = createPresenter();
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        mFab.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFab.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        animation.setRepeatMode(Animation.REVERSE);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//                startOtherActivity(view);
                dialogShow(mEtUser.getText().toString());
            }
        });
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        Log.i(TAG, "heapSize: " + heapSize + "MB");

        mShimmer = new Shimmer();
        mShimmer.start(mShimmerTv);
        /*mShimmer.setRepeatCount(0)
                .setDuration(500)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL)
                .setAnimatorListener(new Animator.AnimatorListener() {
                });*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLlLogin.setVisibility(View.VISIBLE);
    }

    @Override
    protected ILoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    public void login(View view){
        if (checkFormat(mEtUser.getText().toString(), mEtPw.getText().toString())) {
            loginPresenter.testLogin(mEtUser.getText().toString(), mEtPw.getText().toString());
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
    public void onLoginResult(User user) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterResult(RegisterResult user) {

    }

    // Fab的跳转事件
    public void startOtherActivity(View view) {
        mLlLogin.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
            startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    private Effectstype effect;
    // test dialog
    private void dialogShow(String stype){
        switch (stype) {
            case "1": effect=Effectstype.Fadein;break;
            case "2": effect=Effectstype.Slideright;break;
            case "3": effect=Effectstype.Slideleft;break;
            case "4": effect=Effectstype.Slidetop;break;
            case "5": effect=Effectstype.SlideBottom;break;
            case "6": effect=Effectstype.Newspager;break;
            case "7": effect=Effectstype.Fall;break;
            case "8": effect=Effectstype.Sidefill;break;
            case "9": effect=Effectstype.Fliph;break;
            case "10": effect=Effectstype.Flipv;break;
            case "11": effect=Effectstype.RotateBottom;break;
            case "12": effect=Effectstype.RotateLeft;break;
            case "13": effect=Effectstype.Slit;break;
            case "14": effect=Effectstype.Shake;break;
            default:  effect=Effectstype.Fadein; break;
        }

        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("Modal Dialog")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.ic_launcher))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("OK")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .setCustomView(R.layout.custom_view, this)         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                        showProgressBtn();
                    }
                })
                .show();
    }

    private void showProgressBtn(){
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.cpb1);
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    circularButton1.setProgress(50);
                } else if (circularButton1.getProgress() == -1) {   // 100
                    circularButton1.setProgress(0);
                } else {
                    circularButton1.setProgress(-1);        // 100
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
}
