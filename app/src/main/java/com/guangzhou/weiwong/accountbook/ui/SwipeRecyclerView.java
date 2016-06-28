package com.guangzhou.weiwong.accountbook.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.guangzhou.weiwong.accountbook.adapter.SwipeRecyclerViewAdapter;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

/**
 * Created by Tower on 2016/5/12.
 */
public class SwipeRecyclerView extends RecyclerView implements SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = getClass().getName();
    private SwipeRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private OnRefreshAndLoadListener listener;
    private boolean isLoading=false;
    public interface OnRefreshAndLoadListener{
        /**
         * 上拉加载
         */
        void onUpLoad();
        /**
         * 下拉刷新
         */
        void onRefresh();
    }

    private static final int HIDE_THRESHOLD = 56;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    private OnHideAndShowListener hideAndShowListener;
    public interface OnHideAndShowListener{
        void onHide();
        void onShow();
    }
    /**
     * 完成所有加载
     */
    public void completeAllLoad(){
        mAdapter.completeLoad();
    }

    /**
     * 单次加载
     */
    public void completeLoad(){
        isLoading=false;
    }

    public void setOnRefreshAndLoadListener(SwipeRefreshLayout mSwipeRefreshWidget, OnRefreshAndLoadListener listener) {
        this.listener = listener;
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    public void setOnHideAndShowListener(OnHideAndShowListener hideAndShowListener){
        this.hideAndShowListener = hideAndShowListener;
    }

    public SwipeRecyclerView(Context context) {
        super(context);
        init();
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        this.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mAdapter == null) return;
                MyLog.i(TAG, "newState:" + newState + ", lastVisibleItem:" + lastVisibleItem
                    + ", mAdapter.isComplete():" + mAdapter.isComplete() + ", isLoading:" + isLoading);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()
                        && !mAdapter.isComplete() && !isLoading) {
                    isLoading = true;
                    listener.onUpLoad();
                }
            }

            //只有数据满屏的时候才会调用该方法  ???
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                MyLog.i(this, "dx: " + dx + ", dy: " + dy);
                MyLog.i(this, "lastVisibleItem: " + lastVisibleItem
                        + ", mLayoutManager.findLastVisibleItemPosition: " + mLayoutManager.findLastVisibleItemPosition());
                if (lastVisibleItem != 0 && lastVisibleItem != mLayoutManager.findLastVisibleItemPosition()) {
                    mAdapter.setIsFullScreen(true);
                }
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

                if (hideAndShowListener == null) return;
                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                // （如果为RecyclerView添加了Header）
                // show views if first item is first visible position and views are hidden
                /*if (firstVisibleItem == 0) {
                    if (!controlsVisible) {
                        hideAndShowListener.onShow();
                        controlsVisible = true;
                    }
                } else */
                {
                    if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                        hideAndShowListener.onHide();
                        controlsVisible = false;
                        scrolledDistance = 0;
                    } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                        hideAndShowListener.onShow();
                        controlsVisible = true;
                        scrolledDistance = 0;
                    }
                }
                if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                    scrolledDistance += dy;
                }
            }

        });

    }

    public void initData() {
        lastVisibleItem = 0;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        try{
            mAdapter = (SwipeRecyclerViewAdapter)adapter;
        }catch(Exception e){
            Toast.makeText(this.getContext(), "适配器要继承SwipeRecyclerViewAdapter", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        try{
            mLayoutManager = (LinearLayoutManager)layout;
        }catch(Exception e){
            Toast.makeText(this.getContext(), "当前版本只支持LinearLayoutManager", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        lastVisibleItem = 0;
        if(mAdapter.isComplete()){//加载过
            mAdapter.refresh();//重新加载         // tower annotation this means nothing and cause rotation after refreshing when all items are not fullscreen
        }
        listener.onRefresh();
    }

    public static abstract class HidingScrollListener extends RecyclerView.OnScrollListener {


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

        }

        public abstract void onHide();
        public abstract void onShow();
    }
}
