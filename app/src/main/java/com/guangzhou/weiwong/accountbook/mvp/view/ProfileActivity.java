package com.guangzhou.weiwong.accountbook.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerProfilePresenterComponent;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IProfilePresenter;
import com.guangzhou.weiwong.accountbook.utils.DialogUtil;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.SpUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseMvpActivity implements IView {
    private final String TAG = getClass().getSimpleName();
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;

    private boolean isExit = false;     // 是否退出账号
    @Bind(R.id.tv_top) TextView mTvTop;
    @Bind(R.id.tv_name) TextView mTvName;
    @Bind(R.id.tv_email) TextView mTvEmail;
    @Bind(R.id.tv_age) TextView mTvAge;
    @Bind(R.id.tv_career) TextView mTvCareer;
    @Bind(R.id.btn_modify_info) Button mBtnModifyInfo;
    @Bind(R.id.btn_modify_pwd) Button mBtnModifyPwd;
    @Bind(R.id.btn_logout) Button mBtnLogout;
    // 总计
    @Bind(R.id.tv_spend_total) TextView mTvSpendTotal;
    @Bind(R.id.tv_earn_total) TextView mTvEarnTotal;
    @Bind(R.id.tv_month_total) TextView mTvMonthTotal;
    @Bind(R.id.tv_day_total) TextView mTvDayTotal;
    @Bind(R.id.tv_time_total) TextView mTvTimeTotal;
    // 个人
    @Bind(R.id.tv_spend_personal) TextView mTvSpendPersonal;
    @Bind(R.id.tv_earn_personal) TextView mTvEarnPersonal;
    @Bind(R.id.tv_month_personal) TextView mTvMonthPersonal;
    @Bind(R.id.tv_day_personal) TextView mTvDayPersonal;
    @Bind(R.id.tv_time_personal) TextView mTvTimePersonal;
    // 分组
    @Bind(R.id.tv_spend_group) TextView mTvSpendGroup;
    @Bind(R.id.tv_earn_group) TextView mTvEarnGroup;
    @Bind(R.id.tv_month_group) TextView mTvMonthGroup;
    @Bind(R.id.tv_day_group) TextView mTvDayGroup;
    @Bind(R.id.tv_time_group) TextView mTvTimeGroup;
    // 对话框
    private View infoRoot, pwdRoot;
    private EditText mEtName;
    private EditText mEtAge;
    private EditText mEtCareer;
    private EditText mEtOldPwd;
    private EditText mEtNewPwd;
    private EditText mEtConfirmPwd;

    private TextView[][] mTvsValues = new TextView[3][];
    private float[][] allData = new float[3][4];
    private String timeRange;

    @Inject IProfilePresenter iProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.i(TAG, "onCreate: " + "start");
        setContentView(R.layout.activity_profile);
        MyLog.i(TAG, "onCreate: " + "after setContentView");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setLogo(getResources().getDrawable(R.drawable.btg_icon_assistivebutton_submit_pressed));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
//        collapsingToolbar.setTitle("Title");

        initView();
        initInfo();
        setupListener();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                iProfilePresenter.getAllData4Per();
            }
        }, 500);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerProfilePresenterComponent.builder().build().inject(this);
        iProfilePresenter.onAttach(this);
    }

    private void initView() {
        mTvsValues[0] = new TextView[]{mTvSpendTotal, mTvEarnTotal, mTvMonthTotal, mTvDayTotal};
        mTvsValues[1] = new TextView[]{mTvSpendPersonal, mTvEarnPersonal, mTvMonthPersonal, mTvDayPersonal};
        mTvsValues[2] = new TextView[]{mTvSpendGroup, mTvEarnGroup, mTvMonthGroup, mTvDayGroup};

        infoRoot = getLayoutInflater().inflate(R.layout.dialog_modify_info, null);
        mEtName = (EditText) infoRoot.findViewById(R.id.et_user);
        mEtAge = (EditText) infoRoot.findViewById(R.id.et_age);
        mEtCareer = (EditText) infoRoot.findViewById(R.id.et_career);

        pwdRoot = getLayoutInflater().inflate(R.layout.dialog_modify_pwd, null);
        mEtOldPwd = (EditText) pwdRoot.findViewById(R.id.et_pw_old);
        mEtNewPwd = (EditText) pwdRoot.findViewById(R.id.et_pw);
        mEtConfirmPwd = (EditText) pwdRoot.findViewById(R.id.et_pw_confirm);
    }

    private void initInfo() {
        mTvTop.setText(SpUtil.getStringPreference(this, SpUtil.KEY_USER_NAME, "Leon"));
        mTvName.setText(SpUtil.getStringPreference(this, SpUtil.KEY_USER_NAME, "Leon"));
        mTvEmail.setText(SpUtil.getStringPreference(this, SpUtil.KEY_USER_EMAIL, "AndroidDeveloper@gmail.com"));
        mTvAge.setText(String.valueOf(SpUtil.getIntPreference(this, SpUtil.KEY_USER_AGE, 24)));
        mTvCareer.setText(SpUtil.getStringPreference(this, SpUtil.KEY_USER_CAREER, "程序员"));
    }

    private void setupListener() {
        mBtnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != infoRoot.getParent()) {
                    infoRoot = getLayoutInflater().inflate(R.layout.dialog_modify_info, null);
                    mEtName = (EditText) infoRoot.findViewById(R.id.et_user);
                    mEtAge = (EditText) infoRoot.findViewById(R.id.et_age);
                    mEtCareer = (EditText) infoRoot.findViewById(R.id.et_career);
                }
                mEtName.setText(SpUtil.getStringPreference(ProfileActivity.this, SpUtil.KEY_USER_NAME, ""));
                mEtAge.setText(String.valueOf(SpUtil.getIntPreference(ProfileActivity.this, SpUtil.KEY_USER_AGE, 24)));
                mEtCareer.setText(SpUtil.getStringPreference(ProfileActivity.this, SpUtil.KEY_USER_CAREER, ""));
                DialogUtil.dialogShow(ProfileActivity.this, DialogUtil.SLIDER_TOP, "modify", infoRoot,
                        new DialogUtil.DialogClickListener() {
                            @Override
                            public void onClick(View view) {
//                                MyLog.i(TAG, "name: " + mEtName.getText().toString());
                                if (TextUtils.isEmpty(mEtName.getText().toString())
                                        || TextUtils.isEmpty(mEtAge.getText().toString())
                                        || TextUtils.isEmpty(mEtCareer.getText().toString())) {
                                    Toast.makeText(ProfileActivity.this, "信息输入不完整，请重新输入。", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                SpUtil.putStringPreference(ProfileActivity.this, SpUtil.KEY_USER_NAME, mEtName.getText().toString());
                                SpUtil.putIntPreference(ProfileActivity.this, SpUtil.KEY_USER_AGE, Integer.valueOf(mEtAge.getText().toString()));
                                SpUtil.putStringPreference(ProfileActivity.this, SpUtil.KEY_USER_CAREER, mEtCareer.getText().toString());
                                initInfo();
                            }
                        });
            }
        });
        mBtnModifyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != pwdRoot.getParent()) {
                    pwdRoot = getLayoutInflater().inflate(R.layout.dialog_modify_pwd, null);
                    mEtOldPwd = (EditText) pwdRoot.findViewById(R.id.et_pw_old);
                    mEtNewPwd = (EditText) pwdRoot.findViewById(R.id.et_pw);
                    mEtConfirmPwd = (EditText) pwdRoot.findViewById(R.id.et_pw_confirm);
                }
                DialogUtil.dialogShow(ProfileActivity.this, DialogUtil.SLIDER_BOTTOM, "modify", pwdRoot,
                        new DialogUtil.DialogClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.dialogShow(ProfileActivity.this, DialogUtil.SHAKE, "确定要退出本账号吗？",
                        new DialogUtil.DialogClickListener() {
                            @Override
                            public void onClick(View v) {
                                SpUtil.putStringPreference(ProfileActivity.this, SpUtil.KEY_USER_EMAIL, "");
                                SpUtil.putStringPreference(ProfileActivity.this, SpUtil.KEY_USER_PASSWORD, "");
                                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                                isExit = true;
                                finish();
                            }
                        });
            }
        });
    }

    private void refreshValue() {
        MyLog.i(this, "allData length: " + allData.length);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < allData[i].length; j++) {
                mTvsValues[i][j].setText(String.valueOf(allData[i][j]));
            }
        }
    }

    private void refreshTimeRange() {
        mTvTimeTotal.setText(timeRange);
        mTvTimePersonal.setText(timeRange);
        mTvTimeGroup.setText(timeRange);
    }

    @Override
    public void finish() {
        if (!isExit) {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        }
        super.finish();
    }

    @Override
    public void onSignResult(String resultMsg) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public <T> void onLoadResult(int type, T bean) {
        if (type == IView.PERSONAL_DATA) {
            allData = (float[][]) bean;
            refreshValue();
        } else if (type == IView.TIME_RANGE) {
            timeRange = (String) bean;
            refreshTimeRange();
        }
    }

    @Override
    public void onCommitResult(String resultMsg) {

    }
}
