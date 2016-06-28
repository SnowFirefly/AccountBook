package com.guangzhou.weiwong.accountbook.mvp.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.ColorUtils;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.adapter.SwipeRecyclerViewAdapter;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerChartPresenterComponent;
import com.guangzhou.weiwong.accountbook.mvp.model.bean.BusData;
import com.guangzhou.weiwong.accountbook.mvp.model.item.DailyItem;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IChartPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.utils.BusProvider;
import com.guangzhou.weiwong.accountbook.utils.Const;
import com.guangzhou.weiwong.accountbook.utils.DialogUtil;
import com.guangzhou.weiwong.accountbook.utils.ImageUtil;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.squareup.otto.Produce;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartsActivity extends BaseMvpActivity implements IView {
    private static final String TIME_MOR = " 00:00:00", TIME_NOON = " 12:00:00";
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.viewPager) ViewPager viewPager;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton mFab;
    @Bind(R.id.tv_date) TextView mTvDate;
    @Bind(R.id.btn_expend) TextView mBtnExpend;
    @Bind(R.id.view_bg_gray) View mViewBg;
    @Inject IChartPresenter iChartPresenter;

    // 查询相关
    private int dataType = Const.DATE_TYPE_DAY;     // 默认单位是天
    private float[] dateData = new float[31], cateData = new float[5];
    private Date begin, end;

    // popupWindow需要的数据
    private ListView listView;
    private List<String> months, monthStr, years, yearStr;
    private ArrayAdapter<String> arrayAdapter;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = new ListView(this);
        listView.setBackgroundColor(getResources().getColor(R.color.colorBgListView));

        initFragment();
        initList();
        initDate();
        createCircularFloatingActionMenu();
        setupListener();
        loadData();
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ColumnChartFragment());
        fragments.add(new LineChartFragment());
        fragments.add(new PieChartFragment());
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        myFragmentPagerAdapter.setList(fragments);
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(myFragmentPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void initList() {
        monthStr = TimeUtil.getMonthStr();
        months = TimeUtil.getMonthList();
        yearStr = TimeUtil.getYearStrList();
        years = TimeUtil.getYearList();
        if (dataType == Const.DATE_TYPE_MONTH) {
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yearStr);
        } else {
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monthStr);
        }
        listView.setAdapter(arrayAdapter);
    }

    // 重新选择年/月/日为显示单位时初始化标题的日期与查询的时间段
    private void initDate() {
        SimpleDateFormat sdf;
        switch (dataType) {
            case Const.DATE_TYPE_YEAR:
                begin = TimeUtil.strToDate(TimeUtil.BEGIN_YEAR + "-1-1" + TIME_MOR);
                end = TimeUtil.strToDate((TimeUtil.getYear() + 1) + "-1-1" + TIME_MOR);
                mTvDate.setText(TimeUtil.BEGIN_YEAR + "年-" + (TimeUtil.getYear()) + "年");
                break;
            case Const.DATE_TYPE_MONTH:
                sdf = new SimpleDateFormat("yyyy年", Locale.getDefault());
                begin = TimeUtil.strToDate(TimeUtil.getYear() + "-1-1" + TIME_MOR);
                begin = TimeUtil.getBeginOfDay(begin);
                end = TimeUtil.strToDate((TimeUtil.getYear() + 1) + "-1-1" + TIME_MOR);
                end = TimeUtil.getBeginOfDay(end);
                mTvDate.setText(sdf.format(begin));
                break;
            case Const.DATE_TYPE_DAY:
                sdf = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
                begin = TimeUtil.getFirstDayOfMonth(TimeUtil.getYear(), TimeUtil.getMonth() - 1);
                begin = TimeUtil.getBeginOfDay(begin);
                if (TimeUtil.getMonth() == 12) {
                    end = TimeUtil.getFirstDayOfMonth(TimeUtil.getYear() + 1, 0);
                } else {
                    end = TimeUtil.getFirstDayOfMonth(TimeUtil.getYear(), TimeUtil.getMonth());
                }
                end = TimeUtil.getBeginOfDay(end);
                mTvDate.setText(sdf.format(begin));
                break;
        }
    }

    // 重新选择年/月/日为显示单位时刷新可选择的日期列表
    private void refreshList(int dataType) {
        this.dataType = dataType;
        initDate();
        arrayAdapter.clear();
        /*if (dataType == Const.DATE_TYPE_MONTH) {
            arrayAdapter.addAll(yearStr);
        } else {
            arrayAdapter.addAll(monthStr);
        }
        arrayAdapter.notifyDataSetChanged();*/
        initList();
        MyLog.i(this, "dataType: " + dataType + ", begin: " + begin + ", end: " + end);
    }

    private void loadData() {
        iChartPresenter.getStatisticData(dataType, begin, end);
        iChartPresenter.getStatisticData(Const.DATA_TYPE_CATE, begin, end);
    }

    /*@Produce
    public BusData produceBusData() {
        return new BusData(dataType, dateData);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getBusInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getBusInstance().unregister(this);
    }

    @Override
    public void onSignResult(String resultMsg) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public <T> void onLoadResult(int type, T bean) {
        if (type == IView.STATISTIC_DATA) {
            /*if (dataType == Const.DATA_TYPE_CATE) {
                cateData = (float[]) bean;
                BusData busData = new BusData(dataType, cateData);
                BusProvider.getBusInstance().post(busData);
            } else {
                dateData = (float[]) bean;
                BusData busData = new BusData(dataType, dateData);
                BusProvider.getBusInstance().post(busData);
            }*/
            BusData busData = (BusData) bean;
            if (busData.getDataType() == Const.DATA_TYPE_CATE) {
                cateData = busData.getData();
            } else {
                dateData = busData.getData();
            }
            BusProvider.getBusInstance().post(busData);
        }
    }

    @Override
    public void onCommitResult(String resultMsg) {

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerChartPresenterComponent.builder().build().inject(this);
        iChartPresenter.onAttach(this);
    }

    private void setupListener() {
        mTvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(listView);
            }
        });
        mBtnExpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(listView);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    initDate();
                } else {
                    switch (dataType) {
                        case Const.DATE_TYPE_MONTH:
                            begin = TimeUtil.strToDate(years.get(position) + "-1-1" + TIME_MOR);
                            end = TimeUtil.strToDate(years.get(position - 1) + "-1-1" + TIME_MOR);
                            mTvDate.setText(yearStr.get(position).replace(" ", ""));
                            break;
                        case Const.DATE_TYPE_DAY:
                            begin = TimeUtil.strToDate(months.get(position) + TIME_MOR);
                            end = TimeUtil.strToDate(months.get(position - 1) + TIME_MOR);
                            mTvDate.setText(monthStr.get(position).replace(" ", ""));
                            break;
                    }
                }
                MyLog.i("OnItemClickListener", "dataType: " + dataType + ", begin: " + begin + ", end: " + end);
                loadData();
                popupWindow.dismiss();
            }
        });
    }

    com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton;
    private void createCircularFloatingActionMenu(){
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_keyboard_control_grey600_48dp);
        actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                .setContentView(icon).build();
        actionButton.setScaleX(0.7f);
        actionButton.setScaleY(0.7f);
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource(R.drawable.ic_sun_grey600_24dp);
        SubActionButton btnYear = itemBuilder.setContentView(itemIcon).build();
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.drawable.ic_moon_grey600_24dp);
        SubActionButton btnMonth = itemBuilder.setContentView(itemIcon1).build();
        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.ic_earth_grey600_24dp);
        SubActionButton btnDay = itemBuilder.setContentView(itemIcon2).build();

        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btnYear)
                .addSubActionView(btnMonth)
                .addSubActionView(btnDay)
                .attachTo(actionButton)
                .build();

        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                mTvDate.setEnabled(false);
                mBtnExpend.setVisibility(View.INVISIBLE);
                refreshList(Const.DATE_TYPE_YEAR);
                loadData();
            }
        });
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                mTvDate.setEnabled(true);
                mBtnExpend.setVisibility(View.VISIBLE);
                refreshList(Const.DATE_TYPE_MONTH);
                loadData();
            }
        });
        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                mTvDate.setEnabled(true);
                mBtnExpend.setVisibility(View.VISIBLE);
                refreshList(Const.DATE_TYPE_DAY);
                loadData();
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<Fragment> fragments) {
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "柱状图";
                case 1:
                    return "折线图";
                case 2:
                    return "饼状图";
                default:
                    return "统计图";
            }
        }
    }

    public void onShowPopup(View view){
//      getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);    // 隐藏底部导航栏
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ImageUtil.dp2px(ChartsActivity.this, 250), false){
            @Override
            public void dismiss() {
                mViewBg.startAnimation(AnimationUtils.loadAnimation(ChartsActivity.this,
                        R.anim.bg_gray_exit));
                mViewBg.setVisibility(View.GONE);
                super.dismiss();
            }
        };
        popupWindow.setAnimationStyle(R.style.DialogBgAnim);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        mViewBg.setVisibility(View.VISIBLE);
        mViewBg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bg_gray_enter));
        popupWindow.showAtLocation(mFab, Gravity.BOTTOM, 0, getNavigationBarHeight(this));
    }

    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public int getDataType() {
        return dataType;
    }

    public float[] getDateData() {
        return dateData;
    }

    public float[] getCateData() {
        return cateData;
    }
}
