package com.guangzhou.weiwong.accountbook.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.ui.PasterEditView;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class PasterActivity extends BaseMvpActivity implements IView {
    private final String TAG = getClass().getName();
    private boolean isEditable;
    private int weekDay = 1;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.pev_note) PasterEditView mPevNote;

    @Bind(R.id.rg_money_type) RadioGroup mRgMoneyType;
    @Bind(R.id.rg_categories) RadioGroup mRgCategories;
    @Bind(R.id.rb_spend) RadioButton mRbSpend;
    @Bind(R.id.rb_earn) RadioButton mRbEarn;
    @Bind(R.id.rb_cate_food) RadioButton mRbood;
    @Bind(R.id.rb_cate_digital) RadioButton mRbDigital;
    @Bind(R.id.rb_cate_daily) RadioButton mRbDaily;
    @Bind(R.id.rb_cate_clothes) RadioButton mRbClothes;
    @Bind(R.id.rb_cate_other) RadioButton mRbOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: " + "start");
        setContentView(R.layout.activity_paster);
        ButterKnife.bind(this);
        Log.i(TAG, "onCreate: " + "after bind");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPevNote.setTypeface(Typeface.createFromAsset(this.getAssets(), "fangzheng_jinglei.ttf"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPevNote.setEnabled(isEditable);
                isEditable = !isEditable;
            }
        });
        mRgCategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MyLog.i(TAG, "checkedId: " + checkedId);
                switch (checkedId) {
                    case R.id.rb_cate_food:
                        break;
                    case R.id.rb_cate_digital:
                        break;
                    case R.id.rb_cate_daily:
                        break;
                    case R.id.rb_cate_clothes:
                        break;
                    case R.id.rb_cate_other:
                        break;
                }
            }
        });
        Log.i(TAG, "onCreate: " + "end");
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    public void onEdit(View view){
        Log.d(TAG, "onEdit()");
        mPevNote.setEnabled(isEditable);
        isEditable = !isEditable;
    }

    @Bind(R.id.view_bg_gray) View mViewBg;
    @Bind(R.id.rl_root) RelativeLayout mRlRoot;
    public void onShowPopup(View v){
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);    // 隐藏底部导航栏

//        View view = View.inflate(this, R.layout.item_list_card_settle, null);
        DatePicker mDatePicker = new DatePicker(this);
        mDatePicker.setDate(2016, 5);
        mDatePicker.setFestivalDisplay(true);
        mDatePicker.setTodayDisplay(true);
        mDatePicker.setHolidayDisplay(true);
        mDatePicker.setMode(DPMode.SINGLE);

        mDatePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Toast.makeText(PasterActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });

        // 最后一个参数false 代表：不与其余布局发生交互， true代表：可以与其余布局发生交互事件
        final PopupWindow popupWindow = new PopupWindow(mDatePicker, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false){
            // 重写popupWindow消失时事件
            @Override
            public void dismiss() {
                // 在pop消失之前，给咱们加的view设置背景渐变出场动画（Android3.0以上的开发环境，这里建议使用属性动画，那样很柔和，视觉效果更棒！）
                mViewBg.startAnimation(AnimationUtils.loadAnimation(PasterActivity.this,
                        R.anim.bg_gray_exit));
                mViewBg.setVisibility(View.GONE);
                super.dismiss();
            }
        };
        // 设置Pop入场动画效果
        popupWindow.setAnimationStyle(R.style.DialogBgAnim);
        // 设置Pop响应内部区域焦点
        popupWindow.setFocusable(true);
        // 设置Pop响应外部区域焦点
        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow弹出软键盘模式（此处为覆盖式）
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 响应返回键必须的语句
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 在显示pop之前，给咱们加的view设置背景渐变入场动画（Android3.0以上的开发环境，这里建议使用属性动画，那样很柔和，视觉效果更棒！）
        mViewBg.setVisibility(View.VISIBLE);
        mViewBg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bg_gray_enter));
        // 依附的父布局自己设定，我这里为了方便，这样写的。
        popupWindow.showAtLocation(mPevNote, Gravity.BOTTOM, 0, getNavigationBarHeight(this));

        Log.i(TAG, "NavigationBarHeight: " + getNavigationBarHeight(this));
        Log.i(TAG, "hasNavigationBar: " + checkDeviceHasNavigationBar(this));
    }

    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static boolean checkDeviceHasNavigationBar(Context activity) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSignResult(String resultMsg) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public <T> void onLoadResult(int type, T bean) {

    }

    @Override
    public void onCommitResult(String resultMsg) {

    }
}
