package com.guangzhou.weiwong.accountbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.ui.RotateImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tower on 2016/5/12.
 */
public abstract class SwipeRecyclerViewAdapter extends RecyclerView.Adapter {
    private final String TAG = getClass().getName();
    private boolean isComplete=false;//是否完成所有数据的加载
    private FooterViewHolder footerHolder;
    private boolean isFullScreen=false;
    protected static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;//最后一个item
    private static final int TYPE_NULL = -1;

    public abstract int GetItemCount();
    /**
     * @return item的viewHolder
     */
    public abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);
    /**
     * bind itemViewHolder
     */
    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "viewType: " + viewType);
        switch (viewType) {
            case TYPE_FOOTER:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_footer, null);
//                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
                footerHolder = new FooterViewHolder(view);
                return footerHolder;
            case TYPE_NULL:
                return new NullViewHolder(new TextView(parent.getContext()));
            case TYPE_ITEM:
                return onCreateItemViewHolder(parent, viewType);
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            if (viewHolder.mRivFooter.getVisibility() == View.VISIBLE
                    && !isComplete) {
                viewHolder.mRivFooter.startRotate();
            }
        } else {
            onBindItemViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return GetItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(!isFullScreen&&position + 1 == getItemCount()){//当屏幕没有占满的时候，最后一个item用空白view代替footerView
            return TYPE_NULL;
        }
        else if (isFullScreen&&position + 1 == getItemCount()) {// 最后一个item设置为footerView
            return TYPE_FOOTER;
        }
        else {
            return TYPE_ITEM;
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.riv_footer) RotateImageView mRivFooter;
        @Bind(R.id.tv_footer) TextView mTvFooter;
        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class NullViewHolder extends RecyclerView.ViewHolder {
        public NullViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 完成所有加载
     */
    public void completeLoad(){
        isComplete=true;
        footerHolder.mRivFooter.stopRotate();
        footerHolder.mRivFooter.setVisibility(View.GONE);
        footerHolder.mTvFooter.setVisibility(View.VISIBLE);
    }
    /**
     * 是否完成所有数据的加载
     * @return
     */
    public boolean isComplete(){
        return isComplete;
    }
    /**
     *  重新刷新数据
     * isComplete
     */
    public void refresh() {
        isComplete = false;
        footerHolder.mRivFooter.setVisibility(View.VISIBLE);
        footerHolder.mTvFooter.setVisibility(View.GONE);
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setIsFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
    }
}
