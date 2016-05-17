package com.guangzhou.weiwong.accountbook.mvp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.adapter.DailyAdapter;
import com.guangzhou.weiwong.accountbook.animators.ItemAnimatorFactory;
import com.guangzhou.weiwong.accountbook.mvp.model.data.DailyItem;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.ui.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DailyActivity extends BaseMvpActivity implements SwipeRecyclerView.OnRefreshAndLoadListener{
    private final String TAG = getClass().getName();
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private LinearLayoutManager mLayoutManager;
    private SwipeRecyclerView mRecyclerView;
    private DailyAdapter adapter;
    private int downCount= 1;
    private int upCount = 1;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tv_title_bar) TextView mTvTitle;
    @Bind(R.id.fab) FloatingActionButton mFab;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(DailyActivity.this, "DOWN", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshWidget.setRefreshing(false);
//				adapter.getList().add(0,"down:"+downCount++);
//				adapter.getList().add(0,"down:"+downCount++);
                    adapter.getList().clear();
                    initList("refresh",11);
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    Toast.makeText(DailyActivity.this, "UP", Toast.LENGTH_SHORT).show();
                    adapter.getList().add(new DailyItem(R.drawable.icon_head0, "Man", 70f,
                            R.drawable.btg_icon_assistivebutton_submit, "M", "2016/05/08",
                            "Fruit", "Food"));
                    adapter.getList().add(new DailyItem(R.drawable.icon_head0, "Man", 30f,
                            R.drawable.btg_icon_assistivebutton_submit, "T", "2016/05/09",
                            "Fruit", "Food"));
                    adapter.getList().add(new DailyItem(R.drawable.icon_head0, "Man", 60f,
                            R.drawable.btg_icon_assistivebutton_submit, "W", "2016/05/10",
                            "Fruit", "Food"));
                    mRecyclerView.completeLoad();
                    if(adapter.getList().size()>15){
                        mRecyclerView.completeAllLoad();
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(DailyActivity.this, "first", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshWidget.setRefreshing(false);
                    onAnimateCreate();
//                    initList("first",11);
                default:
                    break;
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (SwipeRecyclerView) findViewById(R.id.swipeRecyclerView);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.color1, R.color.color2,
                R.color.color3, R.color.color4);
//		mSwipeRefreshWidget.setOnRefreshListener(this);//不需要再设置刷新功能
		mSwipeRefreshWidget.setProgressViewOffset(true, -50, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
                        .getDisplayMetrics()));
        mRecyclerView.setOnRefreshAndLoadListener(mSwipeRefreshWidget, this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new DailyAdapter();


        mSwipeRefreshWidget.setRefreshing(true);
        handler.sendEmptyMessageDelayed(2, 3000);

        // 延迟启动动画效果
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
    }

    private void onAnimateCreate() {
        ViewCompat.animate(mTvTitle).alpha(1.0f).start();
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
                adapter.setItems(DailyItem.getFakeItemsFirst());
                ViewCompat.animate(mFab).setStartDelay(500)
                        .setDuration(500).scaleX(1).scaleY(1).start();
            }
        });
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
        handler.sendEmptyMessageDelayed(0, 3000);
    }


    @Override
    public void onUpLoad() {
        handler.sendEmptyMessageDelayed(1, 2000);

    }


    @Override
    protected IPresenter createPresenter() {
        return null;
    }

}
