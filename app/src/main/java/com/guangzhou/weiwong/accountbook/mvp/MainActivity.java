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

import butterknife.OnClick;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bugtags.library.core.ui.rounded.CircleImageView;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.MainPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.BaseMvpActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ChartsActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.DailyActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.GroupActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.mvp.view.PasterActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ProfileActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.SettleActivity;
import com.guangzhou.weiwong.accountbook.ui.PasterEditView;
import com.guangzhou.weiwong.accountbook.ui.PasterView;
import com.guangzhou.weiwong.accountbook.utils.HttpUtil;

import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity
        implements NavigationView.OnNavigationItemSelectedListener, IView {
    private final String TAG = getClass().getSimpleName();
    private MainPresenter mainPresenter;
    private Typeface mTypeface;

    @Bind(R.id.vs_mon) ViewStub vs_mon;
    @Bind(R.id.vs_tue) ViewStub vs_tue;
    @Bind(R.id.vs_wed) ViewStub vs_wed;
    @Bind(R.id.vs_thu) ViewStub vs_thu;
    @Bind(R.id.vs_fri) ViewStub vs_fri;
    @Bind(R.id.vs_sat) ViewStub vs_sat;
    @Bind(R.id.vs_sun) ViewStub vs_sun;
//    @Bind(R.id.pv_mon) PasterView mPvMon;
//    @Bind(R.id.pv_tue) PasterView mPvTue;
//    @Bind(R.id.pv_wed) PasterView mPvWed;
//    @Bind(R.id.pv_thu) PasterView mPvThu;
//    @Bind(R.id.pv_fri) PasterView mPvFri;
//    @Bind(R.id.pv_sat) PasterView mPvSat;
//    @Bind(R.id.pv_sun) PasterView mPvSun;
    PasterView mPvMon, mPvTue, mPvWed, mPvThu, mPvFri, mPvSat, mPvSun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        pasterView.setTypeface(null, 0);
        mainPresenter = createPresenter();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                testConn();
                if (mPvTue != null) {
                    mPvTue.setText("测试");
                }
                if (mPvMon != null) {
                    mPvMon.setText("测试");
                    mPvMon.setTextColor(Color.BLUE);
                    Log.d(TAG, "settext");
                }
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        Log.i(TAG, "menuItem1.getTitle():" + menuItem.getTitle());
        if (menuItem.hasSubMenu()) {
            Menu subMenu = menuItem.getSubMenu();
            subMenu.add(R.id.group1, 0x666, 0, "Group 0");
            subMenu.add(R.id.group1, 0x667, 0, "Group 1");
            subMenu.add(R.id.group1, 0x668, 0, "Group 2");
//            MenuItem menuItem1 = subMenu.getItem(2);
//            menuItem1.setIcon(getResources().getDrawable(R.drawable.btg_btn_arrow));
        }

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
//                pasterView.invalidate();
                Log.d(TAG, "handler");
//                mPvMon = (PasterView) vs_mon.inflate();
//                mPvTue = (PasterView) vs_tue.inflate();
//                mPvWed = (PasterView) vs_wed.inflate();
//                mPvThu = (PasterView) vs_thu.inflate();
//                mPvFri = (PasterView) vs_fri.inflate();
//                mPvSat = (PasterView) vs_sat.inflate();
//                mPvSun = (PasterView) vs_sun.inflate();
                mTypeface = Typeface.createFromAsset(getAssets(), "fangzheng_jinglei.ttf");
                mPvMon.setContent("星期一", mTypeface);
                mPvTue.setContent("星期二", mTypeface);
                mPvWed.setContent("星期三", mTypeface);
                mPvThu.setContent("星期四", mTypeface);
                mPvFri.setContent("星期五", mTypeface);
                mPvSat.setContent("星期六", mTypeface);
                mPvSun.setContent("星期日", mTypeface);
            }
        }, 5000);
        mHandler.sendEmptyMessageDelayed(1, 100);
        mHandler.sendEmptyMessageDelayed(2, 500);
        mHandler.sendEmptyMessageDelayed(3, 1200);
        mHandler.sendEmptyMessageDelayed(4, 1500);
        mHandler.sendEmptyMessageDelayed(5, 1800);
        mHandler.sendEmptyMessageDelayed(6, 2500);
        mHandler.sendEmptyMessageDelayed(7, 3000);

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "mHandler:" + msg.what);
            switch (msg.what){
                case 1: if (mPvMon == null) {
                    mPvMon = (PasterView) vs_mon.inflate();
                    }
                    mPvMon.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top));
                    break;
                case 2:
                    if (mPvTue == null) {
                        mPvTue = (PasterView) vs_tue.inflate();
                    }
                    mPvTue.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top));
                    break;
                case 3:
                    if (mPvWed == null) {
                        mPvWed = (PasterView) vs_wed.inflate();
                    }
                    mPvWed.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top));
                    break;
                case 4:
                    if (mPvThu == null) {
                        mPvThu = (PasterView) vs_thu.inflate();
                    }
                    mPvThu.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top));
                    break;
                case 5:
                    if (mPvFri == null) {
                        mPvFri = (PasterView) vs_fri.inflate();
                    }
                    mPvFri.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top));
                    break;
                case 6:
                    if (mPvSat == null) {
                        mPvSat = (PasterView) vs_sat.inflate();
                    }
                    mPvSat.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top));
                    break;
                case 7:
                    if (mPvSun == null) {
                        mPvSun = (PasterView) vs_sun.inflate();
                    }
                    mPvSun.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top));
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    String path = "http://www.book4account.com/api.php?method=userGroupCreate" , json = "";
    private String createJson(){
        JSONObject jsonObjectTotal = new JSONObject(),
                jsonObject = new JSONObject(),
                jsonObject1 = new JSONObject();
        try {
            jsonObject.put("username", "tower");
            jsonObject.put("password", "123");
            jsonObject1.put("group_name", "guangzhou");
            jsonObjectTotal.put("user", jsonObject);
            jsonObjectTotal.put("data", jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjectTotal.toString();
    }
    private void testConn(){
        json = createJson();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.print(HttpUtil.httpPostWithJSON(path, json));
//            }
//        }).start();
//        String str1 = et1.getText().toString();
//        String str2 = et1.getText().toString();
//        String str3 = et1.getText().toString();
//        String str4 = et1.getText().toString();
//        if (str1.equals("") || str2.equals("")
//                || et3.equals(""))
//            mainPresenter.createGroup("abc", "2333", "gdut2");
//        else mainPresenter.createGroup(str1, str2, str3);
    }

    @Override
    public void onLoginResult(User user) {

    }

    @Override
    public void onRegisterResult(RegisterResult user) {
        Log.i(TAG, user.toString());
    }







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


    public void onClickMon(View view){
        startActivity(new Intent(this, PasterActivity.class));
    }
}
