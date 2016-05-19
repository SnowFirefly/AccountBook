package com.guangzhou.weiwong.accountbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bugtags.library.core.ui.rounded.CircleImageView;
import com.bumptech.glide.Glide;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.model.data.DailyItem;
import com.guangzhou.weiwong.accountbook.mvp.model.data.SettleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alan on 2016/5/16.
 */
public class SettleAdapter extends SwipeRecyclerViewAdapter {
    private final String TAG = getClass().getSimpleName();
    private List<SettleItem> mItems;

    public List<SettleItem> getmItems() {
        return mItems;
    }

    public SettleAdapter() {
        this.mItems = new ArrayList<>();
    }

    @Override
    public int GetItemCount() {
        return mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_card_settle, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bindTo(mItems.get(position));
        }
    }

    public void setItems(List<SettleItem> items) {
        // 启动动画的关键位, 顺次添加动画效果
        int pos = getItemCount();
        mItems.addAll(items);
        notifyItemRangeInserted(pos, mItems.size());
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_year_month) TextView mTvYearMonth;
        @Bind(R.id.tv_detail_settle) TextView mTvSettleDetail;
        @Bind(R.id.ci_head_settle) CircleImageView mCiHead;
        @Bind(R.id.tv_name_settle) TextView mTvName;
        @Bind(R.id.ci_check_settle) CircleImageView mCiCheck;
        private Context mContext;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindTo(SettleItem settleItem){
            mTvYearMonth.setText(settleItem.getmMonth());
            mTvSettleDetail.setText(settleItem.getmDetail());
            Glide.with(mContext).load(settleItem.getmHeadIconId()).into(mCiHead);
            mTvName.setText(settleItem.getmName());
            Glide.with(mContext).load(settleItem.getmCheckIconId()).into(mCiCheck);
        }
    }
}
