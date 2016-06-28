package com.guangzhou.weiwong.accountbook.mvp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.adapter.DailyAdapter;
import com.guangzhou.weiwong.accountbook.adapter.SwipeRecyclerViewAdapter;
import com.guangzhou.weiwong.accountbook.animators.ItemAnimatorFactory;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerDailyPresenterComponent;
import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.bean.Record;
import com.guangzhou.weiwong.accountbook.mvp.model.item.DailyItem;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IDailyPresenter;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.ui.SwipeRecyclerView;
import com.guangzhou.weiwong.accountbook.utils.BusProvider;
import com.guangzhou.weiwong.accountbook.utils.DialogUtil;
import com.guangzhou.weiwong.accountbook.utils.ImageUtil;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.guangzhou.weiwong.accountbook.utils.WindowUtil;
import com.squareup.otto.Produce;
import com.wong.greendao.TableRecordPersonal;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DailyActivity extends BaseMvpActivity implements IView, SwipeRecyclerView.OnRefreshAndLoadListener{
    private final String TAG = getClass().getName();
    private static final int ACTION_LOAD_FIRST = 0, ACTION_LOAD_MORE = 1, ACTION_LOAD_REFRESH = 2;
    private static final String TIME_MOR = " 00:00:00", TIME_NOON = " 12:00:00";
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private LinearLayoutManager mLayoutManager;
    private SwipeRecyclerView mRecyclerView;
    private DailyAdapter adapter;
    private List<DailyItem> items;
    private Date begin, end;
    private SimpleDateFormat sdf;
    private int showCount = 0;
    private boolean isNeedAnim = true;
    private ListView listView;
    private List<String> months, monthStr;
    private ArrayAdapter<String> arrayAdapter;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tv_title_bar) TextView mTvTitle;
    @Bind(R.id.tv_date) TextView mTvDate;
    @Bind(R.id.btn_expend) Button mBtnExpend;
    @Bind(R.id.fab) FloatingActionButton mFab;

    @Inject IDailyPresenter iDailyPresenter;
    private DailyHandler dailyHandler;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDailyPresenterComponent.builder().build().inject(this);
        iDailyPresenter.onAttach(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        dailyHandler = new DailyHandler(this);
        initRecyclerView();
        initPopup();
        initDate();
        setupListener();

        mSwipeRefreshWidget.setRefreshing(true);
        dailyHandler.sendEmptyMessageDelayed(ACTION_LOAD_FIRST, 2000);
        iDailyPresenter.getDailyData(0, begin, end);

        BusProvider.getBusInstance().register(this);
    }

    private static class DailyHandler extends Handler {
        private WeakReference<DailyActivity> mAcitivity;

        public DailyHandler(DailyActivity acitivity) {
            this.mAcitivity = new WeakReference<DailyActivity>(acitivity);
        }

        @Override
        public void handleMessage(Message msg) {
            DailyActivity dailyActivity = mAcitivity.get();
            switch (msg.what) {
                case ACTION_LOAD_REFRESH:
//                    Toast.makeText(dailyActivity, "DOWN", Toast.LENGTH_SHORT).show();
                    dailyActivity.mSwipeRefreshWidget.setRefreshing(false);
                    dailyActivity.adapter.getList().clear();
                    dailyActivity.showCount = 0;
                    dailyActivity.addItems();
                    break;
                case ACTION_LOAD_MORE:
//                    Toast.makeText(dailyActivity, "UP", Toast.LENGTH_SHORT).show();
                    dailyActivity.addItems();
                    break;
                case ACTION_LOAD_FIRST:
//                    Toast.makeText(dailyActivity, "first", Toast.LENGTH_SHORT).show();
                    dailyActivity.mSwipeRefreshWidget.setRefreshing(false);
                    dailyActivity.onAnimateCreate();
                default:
                    break;
            }
        }
    }

    private void initRecyclerView() {
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (SwipeRecyclerView) findViewById(R.id.swipeRecyclerView);
//        mSwipeRefreshWidget.setColorSchemeResources(R.color.color1, R.color.color2,
//                R.color.color3, R.color.color4);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent);
//		mSwipeRefreshWidget.setOnRefreshListener(this);//不需要再设置刷新功能
        mSwipeRefreshWidget.setProgressViewOffset(true, -50, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
                        .getDisplayMetrics()));
        mRecyclerView.setOnRefreshAndLoadListener(mSwipeRefreshWidget, this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initPopup() {
        listView = new ListView(this);
        listView.setBackgroundColor(getResources().getColor(R.color.colorBgListView));
        adapter = new DailyAdapter();
        months = TimeUtil.getMonthList();
        monthStr = TimeUtil.getMonthStr();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, monthStr);
        listView.setAdapter(arrayAdapter);
    }

    private void initDate() {
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
                    begin = TimeUtil.strToDate(months.get(position) + TIME_MOR);
                    end = TimeUtil.strToDate(months.get(position - 1) + TIME_MOR);
                    mTvDate.setText(monthStr.get(position).replace(" ", ""));
                }
                mSwipeRefreshWidget.setRefreshing(true);
                mRecyclerView.onRefresh();
                adapter.setIsFullScreen(false);
                adapter.initStatus();
                adapter.getList().clear();
                adapter.notifyDataSetChanged();
                dailyHandler.sendEmptyMessageDelayed(ACTION_LOAD_REFRESH, 2000);
                MyLog.i(this, "begin: " + begin + ", end: " + end);
                iDailyPresenter.getDailyData(0, begin, end);
                popupWindow.dismiss();
            }
        });
        adapter.setOnItemClickListener(new SwipeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
//                Toast.makeText(DailyActivity.this, position + " click", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DailyActivity.this, PasterActivity.class));
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                dailyHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BusProvider.getBusInstance().post(table2Record(items.get(position).getmTableId()));
                        MyLog.i(this, "position: " + position);
                    }
                }, 2000);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
//                Toast.makeText(DailyActivity.this, position + " long click", Toast.LENGTH_LONG).show();
                DialogUtil.dialogShow(DailyActivity.this, DialogUtil.FADEIN, "删除这条记录？", new DialogUtil.DialogClickListener() {
                    @Override
                    public void onClick(View v) {
                        iDailyPresenter.deleteOneRecord(items.get(position).getmTableId());
                        items.remove(position);
                        adapter.getList().remove(position);
                        adapter.notifyItemRemoved(position);
//                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public Record table2Record(long tableId) {
        TableRecordPersonal personal = iDailyPresenter.getOneTableRecord(tableId);
        return new Record(personal.getId(), personal.getBuy_time(), "", 0, personal.getBuyer(), personal.getBuyer_id(),
                personal.getCategory_name(),personal.getCategory_id(), personal.getKind(),
                personal.getMoney(), personal.getDetail(), personal.getState());
    }

    private void onAnimateCreate() {
        ViewCompat.animate(mTvTitle).alpha(1.0f).start();
        ViewCompat.animate(mTvDate).alpha(1.0f).start();
        ViewCompat.animate(mBtnExpend).alpha(1.0f).start();
        mRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
        mRecyclerView.setAdapter(adapter);
        mToolbar.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mToolbar.getViewTreeObserver().removeOnPreDrawListener(this);
                final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                mToolbar.measure(widthSpec, heightSpec);
                collapseToolbar(mToolbar.getHeight());
                return true;
            }
        });
    }

    // Toolbar坍塌成ActionBar
    private void collapseToolbar(int height) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        int toolBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        ValueAnimator valueAnimator = ValueAnimator.ofInt(height, toolBarHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                mToolbar.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                adapter.setItems(DailyItem.getFakeItemsFirst());
                ViewCompat.animate(mFab).setStartDelay(500)
                        .setDuration(500).scaleX(1).scaleY(1).start();
                addItems();
            }
        });
        // 延迟启动动画效果
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                adapter.startItemsAnim();
            }
        }, 2000);
    }

    private void addItems() {
        if (items == null || items.size() == 0){
            adapter.notifyDataSetChanged();
            return;
        }
        List<DailyItem> tempList = new ArrayList<>();
        MyLog.i(this, "showCount: " + showCount + ", items.size: " + items.size());
        if (showCount < 10) {              // 开始只显示10条记录
            if (items.size() <= 10) {
                tempList.addAll(items);
                mRecyclerView.completeAllLoad();          // 换月份时出现footerHolder空指针
            } else {
                for (int i = 0; i < 10; i++) {
                    tempList.add(items.get(i));
                }
                showCount += 10;
            }
            if (isNeedAnim) {
                adapter.setItems(tempList);     // 第二次使用会报IndexOutOfBounds
                isNeedAnim = false;
            } else {
                mRecyclerView.completeLoad();
                adapter.getList().addAll(tempList);
                adapter.notifyDataSetChanged();
            }
        } else {            // 加载第10~20条、20~30、...
            if (items.size() <= (showCount + 10)) {     // 加载完所有记录
                for (int i = showCount; i < items.size(); i++) {
                    tempList.add(items.get(i));
                }
                mRecyclerView.completeAllLoad();
            } else {        // 还有记录未加载
                for (int i = showCount; i < (showCount + 10); i++) {
                    tempList.add(items.get(i));
                }
                showCount += 10;
                mRecyclerView.completeLoad();
            }
            adapter.getList().addAll(tempList);
            adapter.notifyDataSetChanged();
        }
    }

    private void initList(String str,int size) {
        List<String> list = new ArrayList<String>();
        for (int i = 1; i < size; i++) {
            list.add(str+":" + i);
        }
        adapter.getList().addAll(DailyItem.getFakeItemsFirst());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        dailyHandler.sendEmptyMessageDelayed(ACTION_LOAD_REFRESH, 2000);
        iDailyPresenter.getDailyData(0, begin, end);
    }


    @Override
    public void onUpLoad() {
        MyLog.d(this, "onUpLoad");
        dailyHandler.sendEmptyMessageDelayed(ACTION_LOAD_MORE, 2000);

    }

    @Override
    public void onSignResult(String resultMsg) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public <T> void onLoadResult(int type, T bean) {
        MyLog.i(this, "type: " + type);
        if (type == IView.DAILY_DATA) {
            items = (List<DailyItem>) bean;
        }
    }

    @Override
    public void onCommitResult(String resultMsg) {

    }

    @Bind(R.id.view_bg_gray) View mViewBg;
    private PopupWindow popupWindow;
    public void onShowPopup(View view){
//      getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);    // 隐藏底部导航栏
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ImageUtil.dp2px(DailyActivity.this, 250), false){
            @Override
            public void dismiss() {
                mViewBg.startAnimation(AnimationUtils.loadAnimation(DailyActivity.this,
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

    public static boolean checkDeviceHasNavigationBar(Context activity) {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 有一个导航栏
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getBusInstance().unregister(this);
    }
}
