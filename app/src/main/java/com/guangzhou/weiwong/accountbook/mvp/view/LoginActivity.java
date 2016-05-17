package com.guangzhou.weiwong.accountbook.mvp.view;

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
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.model.NetworkApiService;
import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;
import com.guangzhou.weiwong.accountbook.mvp.presenter.ILoginPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseMvpActivity implements IView{
    private final String TAG = getClass().getName();
    private FloatingActionButton mFab;
    @Bind(R.id.et_user) EditText mEtUser;
    @Bind(R.id.et_pw) EditText mEtPw;
    @Bind(R.id.ll_login) LinearLayout mLlLogin;

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
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                startOtherActivity(view);
            }
        });
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        Log.i(TAG, "heapSize: " + heapSize + "MB");
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
}
