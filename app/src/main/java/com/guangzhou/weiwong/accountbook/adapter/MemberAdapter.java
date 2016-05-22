package com.guangzhou.weiwong.accountbook.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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
import com.guangzhou.weiwong.accountbook.mvp.model.data.MemberItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alan on 2016/5/21.
 */
public class MemberAdapter extends RecyclerView.Adapter {
    private final String TAG = getClass().getSimpleName();
    private List<MemberItem> members;
    private List<ItemViewHolder> viewHolders;

    public MemberAdapter() {
        this.members = new ArrayList<>();
        viewHolders = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_member, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bindTo(members.get(position));
        viewHolders.add((ItemViewHolder) holder);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void setItems(List<MemberItem> members){
        int pos = getItemCount();
        this.members.addAll(members);
        notifyItemRangeInserted(pos, members.size());
    }

    public void startItemAnim(){
        Handler handler = new Handler(Looper.myLooper());
        int i = 1;
        for (final ItemViewHolder holder: viewHolders) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.startAnim();
                }
            }, 500 * (i++));
        }
    }

    public void cancelItemAnim(){
        for (ItemViewHolder holder: viewHolders) {
            holder.cancelAnim();
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ci_head) CircleImageView mCiHeadIcon;
        @Bind(R.id.tv_name) TextView mTvName;
        private Context mContext;
        private View view;      // for anim
        private Animation animation;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            view = itemView;
        }

        public void startAnim(){
            if (animation == null) {
                animation = AnimationUtils.loadAnimation(mContext, R.anim.floating);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.startAnimation(animation);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
            view.startAnimation(animation);
        }

        public void cancelAnim(){
            if (animation != null && animation.hasStarted()) {
            }
            animation.setAnimationListener(null);
            view.clearAnimation();
        }

        public void bindTo(MemberItem memberItem){
            Glide.with(mContext).load(R.drawable.icon_head0).into(mCiHeadIcon);
            mTvName.setText(memberItem.getName());
        }
    }
}
