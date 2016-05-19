package com.guangzhou.weiwong.accountbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bugtags.library.core.ui.rounded.CircleImageView;
import com.bumptech.glide.Glide;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.model.data.DailyItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tower on 2016/5/12.
 */
public class DailyAdapter extends SwipeRecyclerViewAdapter{
    private final String TAG = getClass().getName();
    private List<DailyItem> mItems;
    public List<DailyItem> getList() {
        return mItems;
    }

    private List<ItemViewHolder> mHolders;   // test anim
    private static Animation mAnimation;

    public DailyAdapter() {
        mItems = new ArrayList<DailyItem>();
        mHolders = new ArrayList<>();

    }

    @Override
    public int GetItemCount() {
        return mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_card, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            view.startAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.slide_in_top));
//            view.setAnimation(AnimationUtils.loadAnimation(parent.getContext(), R.anim.slide_in_top));
            mAnimation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.shake);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bindTo(mItems.get(position));

            mHolders.add((ItemViewHolder) holder);// test anim
        }
    }

    // test anim
    public void startItemsAnim(){
        for (ItemViewHolder holder: mHolders){
            holder.startViewAnim();
        }
//        for (int i = 0; i < 1; i++) {
//            mHolders.get(i).startViewAnim();
//        }
    }

    public void setItems(List<DailyItem> items) {
        // 启动动画的关键位, 顺次添加动画效果
        int pos = getItemCount();
        mItems.addAll(items);
        notifyItemRangeInserted(pos, mItems.size());
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ci_head) CircleImageView mCiHead;
        @Bind(R.id.tv_name)TextView mTvName;
        @Bind(R.id.tv_money)TextView mTvMoney;
        @Bind(R.id.ci_type) CircleImageView mCiType;
        @Bind(R.id.tv_category)TextView mTvCategory;
        @Bind(R.id.tv_date)TextView mTvDate;
        @Bind(R.id.tv_week)TextView mTvWeek;
        @Bind(R.id.tv_detail)TextView mTvDetail;

        private Context mContext;

        private View mView; // test anim

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mContext = view.getContext();

            mView = view;   // test anim
        }

        // test anim
        public void startViewAnim(){
            mView.startAnimation(mAnimation);
        }

        public void bindTo(DailyItem dailyItem){
            Glide.with(mContext).load(dailyItem.getmHeadImgId()).into(mCiHead);
            mTvName.setText(dailyItem.getmName());
            mTvMoney.setText(String.valueOf(dailyItem.getmMoney()));
            Glide.with(mContext).load(dailyItem.getmTypeIconId()).into(mCiType);
            mTvCategory.setText(dailyItem.getmCategory());
            mTvDate.setText(dailyItem.getmDate());
            mTvWeek.setText(dailyItem.getmWeek());
            mTvDetail.setText(dailyItem.getmDetail());
        }
    }
}
