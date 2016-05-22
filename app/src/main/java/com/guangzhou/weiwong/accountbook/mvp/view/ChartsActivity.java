package com.guangzhou.weiwong.accountbook.mvp.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartsActivity extends BaseMvpActivity {
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.viewPager) ViewPager viewPager;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ColumnChartFragment();
                    case 1:
                        return new LineChartFragment();
                    case 2:
                        return new PieChartFragment();
                    default:
                        return new LineChartFragment();
                }
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
        });

        tabLayout.setupWithViewPager(viewPager);

        createCircularFloatingActionMenu();
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton;
    private void createCircularFloatingActionMenu(){
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_keyboard_control_grey600_48dp);
        actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                .setContentView(icon).build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource(R.drawable.ic_email_grey600_24dp);
        SubActionButton button = itemBuilder.setContentView(itemIcon).build();
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.drawable.ic_remove_red_eye_grey600_24dp);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.ic_keyboard_control_grey600_24dp);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .attachTo(actionButton)
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
            }
        });
    }
}
