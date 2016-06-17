package com.guangzhou.weiwong.accountbook.mvp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bugtags.library.core.ui.rounded.CircleImageView;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerMainPresenterComponent;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IMainPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.MainPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.BaseMvpActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ChartsActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.DailyActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.GroupActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.mvp.view.PasterActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ProfileActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.SettleActivity;
import com.guangzhou.weiwong.accountbook.ui.PasterView;

import com.github.mikephil.charting.charts.LineChart;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.WindowUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity
        implements NavigationView.OnNavigationItemSelectedListener, IView {
    private final String TAG = "MainActivity";
    public static final String WEEKDAY = "WEEKDAY";
    @Inject IMainPresenter iMainPresenter;
    private Typeface mTypeface;
    private WeekClickListener weekClickListener;
    private MyHandler myHandler;

    @Bind(R.id.vs_mon) ViewStub vs_mon;
    @Bind(R.id.vs_tue) ViewStub vs_tue;
    @Bind(R.id.vs_wed) ViewStub vs_wed;
    @Bind(R.id.vs_thu) ViewStub vs_thu;
    @Bind(R.id.vs_fri) ViewStub vs_fri;
    @Bind(R.id.vs_sat) ViewStub vs_sat;
    @Bind(R.id.vs_sun) ViewStub vs_sun;
    PasterView mPvMon, mPvTue, mPvWed, mPvThu, mPvFri, mPvSat, mPvSun;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainPresenterComponent.builder().build().inject(this);
        iMainPresenter.onAttach(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestData();
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        CircleImageView mCiHead = (CircleImageView) view.findViewById(R.id.ci_head);
        mCiHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "handler");
                mTypeface = Typeface.createFromAsset(getAssets(), "fangzheng_jinglei.ttf");
                mPvMon.setContent("星期一\nLuffy支出20\nSix支出25", mTypeface);
                mPvTue.setContent("星期二\nLuffy支出10\nSix支出23", mTypeface);
                mPvWed.setContent("星期三\nLuffy支出20\nSix支出21", mTypeface);
                mPvThu.setContent("星期四\nLuffy支出30\nSix支出24", mTypeface);
                mPvFri.setContent("星期五\nLuffy支出24\nSix支出25", mTypeface);
                mPvSat.setContent("星期六\nLuffy支出21\nSix支出35", mTypeface);
                mPvSun.setContent("星期日\nLuffy支出20\nSix支出15", mTypeface);
            }
        }, 4000);
        myHandler = new MyHandler(this);
        myHandler.sendEmptyMessageDelayed(1, 100);
        myHandler.sendEmptyMessageDelayed(2, 500);
        myHandler.sendEmptyMessageDelayed(3, 1200);
        myHandler.sendEmptyMessageDelayed(4, 1500);
        myHandler.sendEmptyMessageDelayed(5, 1800);
        myHandler.sendEmptyMessageDelayed(6, 2500);
        myHandler.sendEmptyMessageDelayed(7, 3000);

        createMenu();
        weekClickListener = new WeekClickListener();

    }

    private int[] menuItemIDs;      // 分组的菜单项ID
    private void createMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        MyLog.i(TAG, "menuItem1.getTitle():" + menuItem.getTitle());
        if (menuItem.hasSubMenu()) {
            Menu subMenu = menuItem.getSubMenu();
            subMenu.add(R.id.group1, 0x666, 0, "Group 0");
//            subMenu.add(R.id.group1, 0x667, 0, "Group 1");
//            subMenu.add(R.id.group1, 0x668, 0, "Group 2");
//            MenuItem menuItem1 = subMenu.getItem(2);
//            menuItem1.setIcon(getResources().getDrawable(R.drawable.btg_btn_arrow));
        }
    }

    private void requestData() {
        WindowUtil.showPopupWindow(MainActivity.this);
        iMainPresenter.getGroupData();
        iMainPresenter.getCurWeekData(new Date(116, 6, 1), new Date());
    }

    /**
     * 加载7天的贴纸，为其设置监听，并开始动画。
     */
    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity mActivity) {
            this.mActivity = new WeakReference<MainActivity>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity = mActivity.get();
            Log.d(mainActivity.TAG, "mHandler:" + msg.what);
            switch (msg.what){
                case 1:
                    if (mainActivity.mPvMon == null) {
                    mainActivity.mPvMon = (PasterView) mainActivity.vs_mon.inflate();
                    mainActivity.mPvMon.setOnClickListener(mainActivity.weekClickListener);
                    }
                    mainActivity.mPvMon.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 2:
                    if (mainActivity.mPvTue == null) {
                        mainActivity.mPvTue = (PasterView) mainActivity.vs_tue.inflate();
                        mainActivity.mPvTue.setOnClickListener(mainActivity.weekClickListener);
                    }
                    mainActivity.mPvTue.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 3:
                    if (mainActivity.mPvWed == null) {
                        mainActivity.mPvWed = (PasterView) mainActivity.vs_wed.inflate();
                        mainActivity.mPvWed.setOnClickListener(mainActivity.weekClickListener);
                    }
                    mainActivity.mPvWed.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 4:
                    if (mainActivity.mPvThu == null) {
                        mainActivity.mPvThu = (PasterView) mainActivity.vs_thu.inflate();
                        mainActivity.mPvThu.setOnClickListener(mainActivity.weekClickListener);
                    }
                    mainActivity.mPvThu.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 5:
                    if (mainActivity.mPvFri == null) {
                        mainActivity.mPvFri = (PasterView) mainActivity.vs_fri.inflate();
                        mainActivity.mPvFri.setOnClickListener(mainActivity.weekClickListener);
                    }
                    mainActivity.mPvFri.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 6:
                    if (mainActivity.mPvSat == null) {
                        mainActivity.mPvSat = (PasterView) mainActivity.vs_sat.inflate();
                        mainActivity.mPvSat.setOnClickListener(mainActivity.weekClickListener);
                    }
                    mainActivity.mPvSat.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 7:
                    if (mainActivity.mPvSun == null) {
                        mainActivity.mPvSun = (PasterView) mainActivity.vs_sun.inflate();
                        mainActivity.mPvSun.setOnClickListener(mainActivity.weekClickListener);
                    }
                    mainActivity.mPvSun.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    mainActivity.myHandler.sendEmptyMessageDelayed(8, 1000);
                    break;
                case 8:
                    mainActivity.requestData();         // 请求数据
                    break;
                default:    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        MyLog.d(this, "onStop");
        if (WindowUtil.isShown()) {
            WindowUtil.hidePopupWindow();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        MyLog.d(TAG, "onBackPressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (WindowUtil.isShown()) {
            WindowUtil.hidePopupWindow();
            return;
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MyLog.i(this, "onKeyDown: keyCode = " + keyCode);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            WindowUtil.showPopupWindow(MainActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_daily) {
            startActivity(new Intent(this, DailyActivity.class));
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        } else if (id == R.id.nav_charts) {
            startActivity(new Intent(this, ChartsActivity.class));
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        } else if (id == R.id.nav_settle) {
            startActivity(new Intent(this, SettleActivity.class));
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        } else if (id == R.id.nav_groups_manage) {
            startActivity(new Intent(this, GroupActivity.class));
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        } else if (id == R.id.nav_private) {

        } else if (id == R.id.nav_groups) {

        }

        else if (id == 0x666) {
            item.setChecked(true);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 贴纸监听，进入当天记录编辑界面。
     */
    private class WeekClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int weekDay = 1;
            Intent intent = new Intent(MainActivity.this, PasterActivity.class);
            switch (v.getId()) {
                case R.id.pv_mon:   weekDay = 1;   break;
                case R.id.pv_tue:   weekDay = 2;   break;
                case R.id.pv_wed:   weekDay = 3;   break;
                case R.id.pv_thu:   weekDay = 4;   break;
                case R.id.pv_fri:   weekDay = 5;   break;
                case R.id.pv_sat:   weekDay = 6;   break;
                case R.id.pv_sun:   weekDay = 7;   break;
            }
            MyLog.i(TAG, "onClickWeek: " + weekDay);
            intent.putExtra(WEEKDAY, weekDay);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    }

    @Override
    public void onSignResult(String resultMsg) {}

    @Override
    public void onError(String errorMsg) {
        WindowUtil.hidePopupWindow();
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        final Snackbar snackbar = Snackbar.make(fab, errorMsg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMainPresenter.getLocalData(new Date(116, 6, 1), new Date());
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public <T> void onLoadResult(int type, T bean) {
        WindowUtil.hidePopupWindow();
        MyLog.i(this, "type: " + type);
        switch (type) {
            case CUR_WEEK_DATA:

                break;
            case GROUP_DATA:

                break;
        }
    }

    @Override
    public void onCommitResult(String resultMsg) {}




    String[] mMonths = {"Jan", "Feb", "Mar", "April", "May", "June", "July",
            "Aug", "Sept", "Oct", "Nov", "Dec"};
    public void testChart(View view){
        // 自定义字体
        mTf = Typeface.createFromAsset(getAssets(), "huawen_xingkai.ttf");
        // 生产数据
        LineData data = getData(36, 100);
//        mCharts[0] = (LineChart) findViewById(R.id.lc_01);
        for (int i = 0; i < mCharts.length; i++) {
            // add some transparency to the color with "& 0x90FFFFFF"
            setupChart(mCharts[i], data, mColors[i % mColors.length]);
        }
        startActivity(new Intent(this, PasterActivity.class));
    }

    LineChart[] mCharts = new LineChart[1]; // 4条数据
    Typeface mTf; // 自定义显示字体
    int[] mColors = new int[] { Color.rgb(137, 230, 81), Color.rgb(240, 240, 30),//
            Color.rgb(89, 199, 250), Color.rgb(250, 104, 104) }; // 自定义颜色
    // 设置显示的样式
    void setupChart(LineChart chart, LineData data, int color) {
        // if enabled, the chart will always start at zero on the y-axis
//        chart.setStartAtZero(true);

        // disable the drawing of values into the chart
//        chart.setDrawYValues(false);

//        chart.setDrawBorder(false);

        // no description text
        chart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        chart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid lines
//        chart.setDrawVerticalGrid(false); // 是否显示水平的表格
        // mChart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false); // 是否显示表格颜色
//        chart.setGridColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
//        chart.setGridWidth(1.25f);// 表格线的线宽

        // enable touch gestures
        chart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        chart.setDragEnabled(true);// 是否可以拖拽
        chart.setScaleEnabled(true);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);//

        chart.setBackgroundColor(color);// 设置背景

//        chart.setValueTypeface(mTf);// 设置字体

        // add data
        chart.setData(data); // 设置数据

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend(); // 设置标示，就是那个一组y的value的

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.CIRCLE);// 样式
        l.setFormSize(6f);// 字体
        l.setTextColor(Color.WHITE);// 颜色
        l.setTypeface(mTf);// 字体

//        YLabels y = chart.getYLabels(); // y轴的标示
//        y.setTextColor(Color.WHITE);
//        y.setTypeface(mTf);
//        y.setLabelCount(4); // y轴上的标签的显示的个数

//        XLabels x = chart.getXLabels(); // x轴显示的标签
//        x.setTextColor(Color.WHITE);
//        x.setTypeface(mTf);

        // animate calls invalidate()...
        chart.animateX(2500); // 立即执行的动画,x轴
    }

    // 生成一个数据，
    LineData getData(int count, float range) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xVals.add(mMonths[i % 12]);
        }

        // y轴的数据
        List<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 3;
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f); // 线宽
        set1.setCircleSize(3f);// 显示的圆形大小
        set1.setColor(Color.WHITE);// 显示颜色
        set1.setCircleColor(Color.WHITE);// 圆形的颜色
        set1.setHighLightColor(Color.WHITE); // 高亮的线的颜色

        List<com.github.mikephil.charting.interfaces.datasets.ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        return data;
    }

}
