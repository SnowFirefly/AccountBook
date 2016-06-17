package com.guangzhou.weiwong.accountbook.mvp.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bugtags.library.core.ui.rounded.CircleImageView;
import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.adapter.MemberAdapter;
import com.guangzhou.weiwong.accountbook.animators.ItemAnimatorFactory;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.mvp.model.item.MemberItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupActivity extends BaseMvpActivity {
    @Bind(R.id.ll_group) LinearLayout mLlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
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

        /*CardView cardView = new CardView(this);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText("text");
        textView.setTextSize(50);
        textView.setTextColor(getResources().getColor(R.color.color1));
        cardView.addView(textView);
        cardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mLlRoot.addView(cardView);
        FrameLayout frameLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.item_list_card_settle, null);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        FrameLayout frameLayout1 = (FrameLayout) getLayoutInflater().inflate(R.layout.item_list_card_settle, null);
        frameLayout1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mLlRoot.addView(frameLayout);
        mLlRoot.addView(frameLayout1);*/

        createGroup();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    CircleImageView mCiDelete;
    CircleImageView mCiAdd;
    MemberAdapter memberAdapter;
    private void createGroup(){
//        RelativeLayout mRlGroup = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_one_group, null);
        CardView mCvGroup = (CardView) getLayoutInflater().inflate(R.layout.item_one_group, null);
        RelativeLayout mRlGroup = (RelativeLayout) mCvGroup.findViewById(R.id.rl_container);
        RecyclerView mRvGroup = (RecyclerView) mCvGroup.findViewById(R.id.rv_group);
        mCiDelete = (CircleImageView) mCvGroup.findViewById(R.id.ci_delete);
        mCiAdd = (CircleImageView) mCvGroup.findViewById(R.id.ci_add);

        mRvGroup.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        mRvGroup.setHasFixedSize(true);
        mRvGroup.setItemAnimator(ItemAnimatorFactory.slidein());
        memberAdapter = new MemberAdapter();
        memberAdapter.setItems(MemberItem.getFakeItems());
        mRvGroup.setAdapter(memberAdapter);
        mLlRoot.addView(mCvGroup);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
//                memberAdapter.startItemAnim();
            }
        }, 1000);
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
//                memberAdapter.cancelItemAnim();
            }
        }, 20000);
    }

    public void onDeleteMember(View view){
        hideAddAndDelete();
        memberAdapter.startItemAnim();
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showAddAndDelete();
                memberAdapter.cancelItemAnim();
            }
        }, 8000);
    }

    public void onAddMember(View view){
        hideAddAndDelete();
        memberAdapter.startItemAnim();
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showAddAndDelete();
                memberAdapter.cancelItemAnim();
            }
        }, 8000);
    }

    private void hideAddAndDelete(){
        ViewCompat.animate(mCiAdd)
                .translationX(150)
                .rotation(360)
                .setDuration(500)
                .setInterpolator(new AccelerateInterpolator())
                .start();
        ViewCompat.animate(mCiDelete)
                .translationX(-150)
                .rotation(-360)
                .setDuration(500)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }
    private void showAddAndDelete(){
        ViewCompat.animate(mCiAdd)
                .translationX(0)
                .rotation(-360)
                .setDuration(500)
                .setInterpolator(new AccelerateInterpolator())
                .start();
        ViewCompat.animate(mCiDelete)
                .translationX(0)
                .rotation(360)
                .setDuration(500)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return super.onOptionsItemSelected(item);
    }
 */
}
