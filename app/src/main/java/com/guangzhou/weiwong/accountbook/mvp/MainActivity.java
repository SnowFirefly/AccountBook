package com.guangzhou.weiwong.accountbook.mvp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
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
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.bugtags.library.core.ui.rounded.CircleImageView;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerMainPresenterComponent;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IMainPresenter;
import com.guangzhou.weiwong.accountbook.mvp.view.BaseMvpActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ChartsActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.DailyActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.GroupActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.mvp.view.PasterActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.ProfileActivity;
import com.guangzhou.weiwong.accountbook.mvp.view.SettleActivity;
import com.guangzhou.weiwong.accountbook.ui.PasterView;

import com.guangzhou.weiwong.accountbook.utils.DialogUtil;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.SpUtil;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.guangzhou.weiwong.accountbook.utils.WindowUtil;

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
    private WeekClickListener weekClickListener;
    private MyHandler myHandler;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;

    @Bind(R.id.vs_mon) ViewStub vs_mon;
    @Bind(R.id.vs_tue) ViewStub vs_tue;
    @Bind(R.id.vs_wed) ViewStub vs_wed;
    @Bind(R.id.vs_thu) ViewStub vs_thu;
    @Bind(R.id.vs_fri) ViewStub vs_fri;
    @Bind(R.id.vs_sat) ViewStub vs_sat;
    @Bind(R.id.vs_sun) ViewStub vs_sun;
    private PasterView mPvMon, mPvTue, mPvWed, mPvThu, mPvFri, mPvSat, mPvSun;

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

        init();
        createMenu();
        weekClickListener = new WeekClickListener();
        myHandler = new MyHandler(this);
        myHandler.sendEmptyMessageDelayed(1, 100);
        myHandler.sendEmptyMessageDelayed(2, 500);
        myHandler.sendEmptyMessageDelayed(3, 1200);
        myHandler.sendEmptyMessageDelayed(4, 1500);
        myHandler.sendEmptyMessageDelayed(5, 1800);
        myHandler.sendEmptyMessageDelayed(6, 2500);
        myHandler.sendEmptyMessageDelayed(7, 3000);

        MyLog.i(this, "invocation: " + Bugtags.currentInvocationEvent());
        Bugtags.setInvocationEvent(Bugtags.BTGInvocationEventShake);
    }

    private void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        CircleImageView mCiHead = (CircleImageView) view.findViewById(R.id.ci_head);
        mCiHead.setImageDrawable(getResources().getDrawable(R.drawable.icon_head0));
        mCiHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 700);
            }
        });
        ((TextView) view.findViewById(R.id.tv_name)).setText(SpUtil.getStringPreference(this,
                SpUtil.KEY_USER_NAME, "Leon"));
        ((TextView) view.findViewById(R.id.tv_email)).setText(SpUtil.getStringPreference(this,
                SpUtil.KEY_USER_EMAIL, "我的共享账本"));
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

    private void setupListener() {
        mPvMon.setOnClickListener(weekClickListener);
        mPvTue.setOnClickListener(weekClickListener);
        mPvWed.setOnClickListener(weekClickListener);
        mPvThu.setOnClickListener(weekClickListener);
        mPvFri.setOnClickListener(weekClickListener);
        mPvSat.setOnClickListener(weekClickListener);
        mPvSun.setOnClickListener(weekClickListener);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestData();
            }
        });
    }

    private void requestData() {
        WindowUtil.showPopupWindow(MainActivity.this);
        iMainPresenter.getGroupData();
        iMainPresenter.getCurWeekData(TimeUtil.getWeekDay(1), TimeUtil.getWeekDay(7));
    }

    /**
     * 加载7天的贴纸，为其设置监听，并开始动画。
     */
    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MainActivity mainActivity = mActivity.get();
            Log.d(mainActivity.TAG, "mHandler:" + msg.what);
            switch (msg.what){
                case 1:
                    if (mainActivity.mPvMon == null) {
                        mainActivity.mPvMon = (PasterView) mainActivity.vs_mon.inflate();
                    }
                    mainActivity.mPvMon.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 2:
                    if (mainActivity.mPvTue == null) {
                        mainActivity.mPvTue = (PasterView) mainActivity.vs_tue.inflate();
                    }
                    mainActivity.mPvTue.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 3:
                    if (mainActivity.mPvWed == null) {
                        mainActivity.mPvWed = (PasterView) mainActivity.vs_wed.inflate();
                    }
                    mainActivity.mPvWed.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 4:
                    if (mainActivity.mPvThu == null) {
                        mainActivity.mPvThu = (PasterView) mainActivity.vs_thu.inflate();
                    }
                    mainActivity.mPvThu.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 5:
                    if (mainActivity.mPvFri == null) {
                        mainActivity.mPvFri = (PasterView) mainActivity.vs_fri.inflate();
                    }
                    mainActivity.mPvFri.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 6:
                    if (mainActivity.mPvSat == null) {
                        mainActivity.mPvSat = (PasterView) mainActivity.vs_sat.inflate();
                    }
                    mainActivity.mPvSat.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    break;
                case 7:
                    if (mainActivity.mPvSun == null) {
                        mainActivity.mPvSun = (PasterView) mainActivity.vs_sun.inflate();
                    }
                    mainActivity.mPvSun.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_top));
                    mainActivity.myHandler.sendEmptyMessageDelayed(8, 1000);
                    break;
                case 8:
                    ViewCompat.animate(mainActivity.fab)
                            .scaleY(1).scaleX(1)
                            .setStartDelay(0)
                            .setDuration(500)
                            .setInterpolator(new DecelerateInterpolator());
                    mainActivity.setupListener();
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
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DialogUtil.dialogShow(this, DialogUtil.FLIPH, "退出共享账本？", new DialogUtil.DialogClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.super.onBackPressed();
                }
            });
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
            Toast.makeText(this, "敬请期待！", Toast.LENGTH_LONG).show();
        }

        else if (id == 0x666) {
            item.setChecked(true);
            Toast.makeText(this, "敬请期待！", Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) drawer.closeDrawer(GravityCompat.START);
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
//        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        final Snackbar snackbar = Snackbar.make(fab, errorMsg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMainPresenter.getLocalData(TimeUtil.getWeekDay(1), TimeUtil.getWeekDay(7));
                snackbar.dismiss();
            }
        });
        snackbar.show();
        iMainPresenter.getLocalData(TimeUtil.getWeekDay(1), TimeUtil.getEndOfDay(TimeUtil.getWeekDay(7)));
    }

    @Override
    public <T> void onLoadResult(int type, T bean) {
        WindowUtil.hidePopupWindow();
        MyLog.i(this, "type: " + type);
        switch (type) {
            case CUR_WEEK_DATA:
                List<String> weekData = (List<String>) bean;
                Typeface mTypeface = MyApplication.getTypeface();
                mPvMon.setContent("星期一" + weekData.get(0), mTypeface);
                mPvTue.setContent("星期二" + weekData.get(1), mTypeface);
                mPvWed.setContent("星期三" + weekData.get(2), mTypeface);
                mPvThu.setContent("星期四" + weekData.get(3), mTypeface);
                mPvFri.setContent("星期五" + weekData.get(4), mTypeface);
                mPvSat.setContent("星期六" + weekData.get(5), mTypeface);
                mPvSun.setContent("星期日" + weekData.get(6), mTypeface);
                break;
            case GROUP_DATA:

                break;
        }
    }

    @Override
    public void onCommitResult(String resultMsg) {}
}
