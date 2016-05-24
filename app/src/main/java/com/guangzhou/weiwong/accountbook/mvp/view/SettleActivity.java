package com.guangzhou.weiwong.accountbook.mvp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.adapter.SettleAdapter;
import com.guangzhou.weiwong.accountbook.animators.ItemAnimatorFactory;
import com.guangzhou.weiwong.accountbook.mvp.model.data.SettleItem;
import com.guangzhou.weiwong.accountbook.ui.HidingScrollListener;
import com.guangzhou.weiwong.accountbook.ui.SwipeRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettleActivity extends BaseMvpActivity implements SwipeRecyclerView.OnRefreshAndLoadListener, SwipeRecyclerView.OnHideAndShowListener {
//    private final String TAG = getClass().getSimpleName();
    @Bind(R.id.appBarLayout) AppBarLayout mAppBarLayout;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.swipeRecyclerView) SwipeRecyclerView mSwipeRecyclerView;
    private SettleAdapter mSettleAdapter;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tv_title_bar) TextView mTvTitle;
    @Bind(R.id.fab) FloatingActionButton mFab;
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.color1, R.color.color2,
                R.color.color3, R.color.color4);
        mSwipeRefreshLayout.setProgressViewOffset(false, -50, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources()
                        .getDisplayMetrics()));
        mSwipeRecyclerView.setOnRefreshAndLoadListener(mSwipeRefreshLayout, this);
        mSwipeRecyclerView.setOnHideAndShowListener(this);
        mSwipeRecyclerView.setHasFixedSize(true);
        mSwipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSettleAdapter = new SettleAdapter();
        mSwipeRefreshLayout.setRefreshing(true);

        mHandler = new MyHandler(this);
        mHandler.sendEmptyMessageDelayed(2, 3000);
    }

    private static class MyHandler extends Handler {
        private WeakReference<SettleActivity> mActivity;

        public MyHandler(SettleActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SettleActivity settleActivity = mActivity.get();
            String detail = "结算详情\n" +
                    "总支出： 700元 平均350元\n" +
                    "Luffy： 支出300元 差额50元 结算情况：未结算\n" +
                    "Six： 支出400元 差额-50元 结算情况：未结算\n";
            switch (msg.what) {
                case 0:
                    Toast.makeText(settleActivity, "DOWN", Toast.LENGTH_SHORT).show();
                    settleActivity.mSwipeRefreshLayout.setRefreshing(false);
                    settleActivity.mSettleAdapter.getmItems().clear();
                    settleActivity.initList("refresh",11);
                    settleActivity.mSettleAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    Toast.makeText(settleActivity, "UP", Toast.LENGTH_SHORT).show();
                    settleActivity.mSettleAdapter.getmItems().add(new SettleItem("16\n年\n9\n月",
                            detail, R.drawable.icon_head0, "Six", R.drawable.btg_icon_priority_1_selected));
                    settleActivity.mSettleAdapter.getmItems().add(new SettleItem("16\n年\n10\n月",
                            detail, R.drawable.icon_head0, "Six", R.drawable.btg_icon_priority_1_selected));

                    settleActivity.mSwipeRecyclerView.completeLoad();
                    if(settleActivity.mSettleAdapter.getmItems().size()>6){
                        settleActivity.mSwipeRecyclerView.completeAllLoad();
                    }
                    settleActivity.mSettleAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(settleActivity, "first", Toast.LENGTH_SHORT).show();
                    settleActivity.mSwipeRefreshLayout.setRefreshing(false);
                    settleActivity.onAnimateCreate();
                    break;
                default:break;
            }
        }
    }

    private void onAnimateCreate() {
        ViewCompat.animate(mTvTitle).alpha(1.0f).start();
        mSwipeRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
        mSwipeRecyclerView.setAdapter(mSettleAdapter);

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
//        ViewCompat.animate(mToolbar)
//                .alpha(0)
//                .setDuration(500)
//                .setInterpolator(new DecelerateInterpolator())
//                .start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mSettleAdapter.setItems(SettleItem.getFakeItemsFirst());
                ViewCompat.animate(mFab).setStartDelay(500)
                        .setDuration(500).scaleX(1).scaleY(1).start();
            }
        });
    }

    private void initList(String str,int size) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            list.add(str+":" + i);
        }
        mSettleAdapter.getmItems().addAll(SettleItem.getFakeItemsFirst());
        mSettleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpLoad() {
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    private void hideToolbar(){
        mAppBarLayout.animate().translationY(-mAppBarLayout.getHeight())
                .setInterpolator(new AccelerateInterpolator(2)).start();
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mFab.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFab.animate().translationY(mFab.getHeight()+fabBottomMargin)
                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showToolbar(){
        mAppBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        mFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    public void onHide() {
        hideToolbar();
    }

    @Override
    public void onShow() {
        showToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return super.onOptionsItemSelected(item);
    }
}
