package com.guangzhou.weiwong.accountbook.mvp.model.item;

import com.guangzhou.weiwong.accountbook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan on 2016/5/16.
 */
public class SettleItem {
    private String mMonth;
    private String mDetail;
    private int mHeadIconId;
    private String mName;
    private int mCheckIconId;

    public SettleItem(String mMonth, String mDetail, int mHeadIconId, String mName, int mCheckIconId) {
        this.mMonth = mMonth;
        this.mDetail = mDetail;
        this.mHeadIconId = mHeadIconId;
        this.mName = mName;
        this.mCheckIconId = mCheckIconId;
    }

    public static List<SettleItem> getFakeItemsFirst(){
        String[] month = { "16\n年\n6\n月", "16\n年\n5\n月", "16\n年\n4\n月", "16\n年\n3\n月" };
        String detail = "结算详情\n" +
                "总支出： 700元 平均350元\n" +
                "Luffy： 支出300元 差额50元 结算情况：未结算\n" +
                "Six： 支出400元 差额-50元 结算情况：未结算\n";
        List<SettleItem> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            items.add(new SettleItem(month[i], detail, R.drawable.icon_head0, "Six", R.drawable.btg_icon_priority_1_selected));
        }
        return items;
    }

    public String getmMonth() {
        return mMonth;
    }

    public String getmDetail() {
        return mDetail;
    }

    public int getmHeadIconId() {
        return mHeadIconId;
    }

    public String getmName() {
        return mName;
    }

    public int getmCheckIconId() {
        return mCheckIconId;
    }
}
