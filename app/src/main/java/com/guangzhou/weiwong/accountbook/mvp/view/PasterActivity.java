package com.guangzhou.weiwong.accountbook.mvp.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerPasterPresenterComponent;
import com.guangzhou.weiwong.accountbook.mvp.MainActivity;
import com.guangzhou.weiwong.accountbook.mvp.MyApplication;
import com.guangzhou.weiwong.accountbook.mvp.model.bean.Record;
import com.guangzhou.weiwong.accountbook.mvp.model.item.DailyItem;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPasterPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.ui.PasterEditView;
import com.guangzhou.weiwong.accountbook.utils.BusProvider;
import com.guangzhou.weiwong.accountbook.utils.CalendarUtil;
import com.guangzhou.weiwong.accountbook.utils.Const;
import com.guangzhou.weiwong.accountbook.utils.DialogUtil;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.SpUtil;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.squareup.otto.Subscribe;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class PasterActivity extends BaseMvpActivity implements IView {
    private final String TAG = getClass().getSimpleName();
    private static final int ACTION_CLOSE_WINDOW = 0, ACTION_SHOW_LISTVIEW = 1;
    private static final String TIME = " 12:00:00";
    private boolean isEditable = false;
    private int weekDay = 1;
    private boolean isNewRecord = true;     // 是否新增记录（否则为修改）
    private long tableId = -1;
    private Date date;
    private String detail;
    private float money;
    private int kind = Const.MONEY_KIND_SPEND;
    private long categoryId = Const.SPEND_CATE_FOOD;
    private String categoryName = "饮食";
    private String userName, email, groupName;
    private long userId, groupId;
    private int state;
    private List<Record> records;
    private ListView listView;
    private List<String> details;
    private ArrayAdapter<String> arrayAdapter;

    @Bind(R.id.view_bg_gray) View mViewBg;
    @Bind(R.id.rl_root) RelativeLayout mRlRoot;
    private PopupWindow popupWindow;
    private DatePicker mDatePicker;

    private SimpleDateFormat sdf;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tv_date) TextView mTvDate;
    @Bind(R.id.btn_expend) Button mBtnExpend;
    private PasterEditView mPevNote;        // 若用注解效率会降低，导致阻塞?
    private FloatingActionButton mFab;
    private PasterHandler pasterHandler;

    @Bind(R.id.et_money) EditText mEtMoney;
    @Bind(R.id.rg_money_type) RadioGroup mRgMoneyType;
    @Bind(R.id.rg_categories) RadioGroup mRgCategories;
    @Bind(R.id.rb_spend) RadioButton mRbSpend;
    @Bind(R.id.rb_earn) RadioButton mRbEarn;
    @Bind(R.id.rb_cate_food) RadioButton mRbFood;
    @Bind(R.id.rb_cate_digital) RadioButton mRbDigital;
    @Bind(R.id.rb_cate_daily) RadioButton mRbDaily;
    @Bind(R.id.rb_cate_clothes) RadioButton mRbClothes;
    @Bind(R.id.rb_cate_other) RadioButton mRbOther;

    @Inject IPasterPresenter iPasterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: " + "start");
        setContentView(R.layout.activity_paster);
        ButterKnife.bind(this);
        Log.i(TAG, "onCreate: " + "after bind");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        setupListener();
        if (weekDay != -1) {
            iPasterPresenter.getOneDayData(date);
        }
        Log.i(TAG, "onCreate: " + "end");
    }

    @Subscribe
    public void receiveRecord(Record record) {
        MyLog.i(this, "receiveRecord record: " + record.getDate());
        refreshData(record);
    }

    private void init() {
        mPevNote = (PasterEditView) findViewById(R.id.pev_note);
        mPevNote.setTypeface(MyApplication.getTypeface());
        mPevNote.setEnabled(false);
        mEtMoney.setEnabled(false);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mDatePicker = new DatePicker(PasterActivity.this);
        mDatePicker.setDate(TimeUtil.getYear(), TimeUtil.getMonth());
        mDatePicker.setFestivalDisplay(true);
        mDatePicker.setTodayDisplay(true);
        mDatePicker.setHolidayDisplay(true);
        mDatePicker.setMode(DPMode.SINGLE);

        details = new ArrayList<>();
        details.add("添加");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, details);

        listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setBackgroundColor(getResources().getColor(R.color.colorBgListView));
        listView.setAdapter(arrayAdapter);

        weekDay = getIntent().getIntExtra(MainActivity.WEEKDAY, -1);
        userName = SpUtil.getStringPreference(this, SpUtil.KEY_USER_NAME, "Leon");
        date = TimeUtil.getNoonOfDay(TimeUtil.getWeekDay(weekDay));
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        mTvDate.setText(sdf.format(date));
        pasterHandler = new PasterHandler(this);
    }

    private void initValue() {
        isNewRecord = true;
        tableId = -1;
        detail = "";
        money = 0;
        kind = Const.MONEY_KIND_SPEND;
        categoryId = Const.SPEND_CATE_FOOD;
        categoryName = "饮食";
        mPevNote.setText("");
        mEtMoney.setText("");
        mRbSpend.setChecked(true);
        mRbFood.setChecked(true);
    }

    private void setupListener() {
        mDatePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Toast.makeText(PasterActivity.this, date, Toast.LENGTH_SHORT).show();
                datePicked(TimeUtil.strToDate(date + TIME));
                pasterHandler.sendEmptyMessageDelayed(ACTION_CLOSE_WINDOW, 200);
            }
        });
        mTvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(mDatePicker);
            }
        });
        mBtnExpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(mDatePicker);
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditable) {
                    detail = mPevNote.getText().toString();
                    if (!TextUtils.isEmpty(detail) && !TextUtils.isEmpty(mEtMoney.getText().toString())) {
                        money = Float.parseFloat(mEtMoney.getText().toString());
                        finishEdit();
                        isEditable = false;
                        mPevNote.setEnabled(false);
                        mEtMoney.setEnabled(false);
                        mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_border_color_white_48dp));
                    } else {
                        Toast.makeText(PasterActivity.this, "请输入详情和金额", Toast.LENGTH_LONG).show();
                    }
                } else {
                    mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud_upload_white_48dp));
                    isEditable = true;
                    mPevNote.setEnabled(true);
                    mEtMoney.setEnabled(true);
                }

            }
        });
        mRgMoneyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_spend) {
                    kind = Const.MONEY_KIND_SPEND;
                } else if (checkedId == R.id.rb_earn) {
                    kind = Const.MONEY_KIND_EARN;
                }
            }
        });
        mRgCategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MyLog.i(TAG, "checkedId: " + checkedId);
                switch (checkedId) {
                    case R.id.rb_cate_food:
                        categoryId = Const.SPEND_CATE_FOOD;
                        categoryName = "饮食";
                        break;
                    case R.id.rb_cate_digital:
                        categoryId = Const.SPEND_CATE_DIGITAL;
                        categoryName = "电子产品";
                        break;
                    case R.id.rb_cate_daily:
                        categoryId = Const.SPEND_CATE_DAILY;
                        categoryName = "日用品";
                        break;
                    case R.id.rb_cate_clothes:
                        categoryId = Const.SPEND_CATE_CLOTHES;
                        categoryName = "服饰";
                        break;
                    case R.id.rb_cate_other:
                        categoryId = Const.SPEND_CATE_OTHER;
                        categoryName = "其他";
                        break;
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (records != null && position < records.size()) {
                    refreshData(records.get(position));
                } else {    // 添加新记录
                    initValue();
                    pasterHandler.sendEmptyMessageDelayed(ACTION_CLOSE_WINDOW, 0);
                }
            }
        });
    }

    private static class PasterHandler extends Handler {
        private WeakReference<PasterActivity> mActivity;

        public PasterHandler(PasterActivity activity) {
            this.mActivity = new WeakReference<PasterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PasterActivity pasterActivity = mActivity.get();
            switch (msg.what) {
                case ACTION_CLOSE_WINDOW:
                    if (pasterActivity.popupWindow.isShowing()) {
                        pasterActivity.popupWindow.dismiss();
                    }
                    break;
                case ACTION_SHOW_LISTVIEW:
                    pasterActivity.onShowPopup(pasterActivity.listView);
                    break;
            }
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerPasterPresenterComponent.builder().build().inject(this);
        iPasterPresenter.onAttach(this);
    }

    private void datePicked(Date date) {
        this.date = date;
        mTvDate.setText(sdf.format(date));
        iPasterPresenter.getOneDayData(date);
    }

    private void finishEdit() {
        if (isNewRecord) {
            tableId = -1;
        }
        Record record = new Record(tableId, date, groupName, groupId,
                userName, userId, categoryName, categoryId, kind, money, detail, state);
        iPasterPresenter.setOneDayData(record);
    }

    private void refreshData(Record record) {
        isNewRecord = false;
        date = record.getDate();
        mTvDate.setText(sdf.format(date));
        mPevNote.setText(record.getDetail());
        mEtMoney.setText("" + record.getMoney());
        tableId = record.getId();
        categoryId = record.getCate();
        categoryName = record.getCateName();
        kind = record.getKind();
        money = record.getMoney();
        detail = record.getDetail();
        state = record.getState();
        if (kind == Const.MONEY_KIND_SPEND) {
            mRbSpend.setChecked(true);
        } else if (kind == Const.MONEY_KIND_EARN) {
            mRbEarn.setChecked(true);
        }
        switch ((int) categoryId) {
            case Const.SPEND_CATE_FOOD: mRbFood.setChecked(true);   break;
            case Const.SPEND_CATE_DAILY: mRbDaily.setChecked(true);   break;
            case Const.SPEND_CATE_DIGITAL: mRbDigital.setChecked(true);   break;
            case Const.SPEND_CATE_CLOTHES: mRbClothes.setChecked(true);   break;
            case Const.SPEND_CATE_OTHER: mRbOther.setChecked(true);   break;
            default:    mRbFood.setChecked(true);   break;
        }
    }

    public void onShowPopup(View view){
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);    // 隐藏底部导航栏

//        View view = View.inflate(this, R.layout.item_list_card_settle, null);

        // 最后一个参数false 代表：不与其余布局发生交互， true代表：可以与其余布局发生交互事件
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
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
        if (isEditable && !TextUtils.isEmpty(mPevNote.getText().toString())) {
            dialogShow();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSignResult(String resultMsg) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public <T> void onLoadResult(int type, T bean) {
        if (type == IView.ONE_DAY_DATA) {
            records = (List<Record>) bean;
            details = new ArrayList<>();
            Record record;
            MyLog.i(this, "records.size: " + records.size());
            for (int i = 0; i < records.size(); i++) {
                record = records.get(i);
                details.add((record.getDetail().length() <= 15 ? record.getDetail() :
                        (record.getDetail().substring(0, 15) + "...")) + "   ¥ " + record.getMoney());
                MyLog.i(this, "record.detail: " + record.getDetail() + ", time: " + record.getDate());
            }
            details.add("添加");
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, details);
            listView.setAdapter(arrayAdapter);
            pasterHandler.sendEmptyMessageDelayed(ACTION_SHOW_LISTVIEW, 1000);
        }
    }

    @Override
    public void onCommitResult(String resultMsg) {

    }

    private void dialogShow(){
        DialogUtil.dialogShow(this, DialogUtil.SLIDER_LEFT, "内容未提交，是否退出编辑？", new DialogUtil.DialogClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getBusInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getBusInstance().unregister(this);
    }
}
