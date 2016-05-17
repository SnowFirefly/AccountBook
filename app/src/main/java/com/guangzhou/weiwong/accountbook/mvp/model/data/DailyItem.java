package com.guangzhou.weiwong.accountbook.mvp.model.data;

import com.guangzhou.weiwong.accountbook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tower on 2016/5/13.
 */
public class DailyItem {
    private int mHeadImgId;
    private String mName;
    private float mMoney;
    private int mTypeIconId;
    private String mDate;
    private String mWeek;
    private String mCategory;
    private String mDetail;

    public DailyItem(int mHeadImgId, String mName, float mMoney, int mTypeIconId,
                     String mWeek, String mDate, String mDetail, String mCategory) {
        this.mHeadImgId = mHeadImgId;
        this.mName = mName;
        this.mMoney = mMoney;
        this.mTypeIconId = mTypeIconId;
        this.mWeek = mWeek;
        this.mDate = mDate;
        this.mDetail = mDetail;
        this.mCategory = mCategory;
    }

    public static List<DailyItem> getFakeItemsFirst(){
        int[] headId = { R.drawable.icon_head0, R.drawable.icon_head1, R.drawable.icon_head2,
                R.drawable.icon_head3, R.drawable.icon_head4, R.drawable.icon_head5,
                R.drawable.icon_head6 };
        int[] typeId = { R.drawable.btg_icon_assistivebutton_submit_pressed, R.drawable.btg_icon_assistivebutton_submit };
        String[] name = { "Luffy", "Six", "Man" };
        String[] date = { "2016/05/01", "2016/05/02", "2016/05/03", "2016/05/04", "2016/05/05",
                "2016/05/06", "2016/05/07"};
        String[] week = { "一", "二", "三", "四", "五", "六", "日"};
        String[] detail = { "花生", "西瓜", "青菜", "鸡蛋", "苹果", "可乐", "沐浴露"};
        ArrayList<DailyItem> itemsList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            itemsList.add(new DailyItem(headId[i], name[i%3], 20.0f + 2,
                    typeId[i%2], week[i], date[i], detail[i], "食物"));
        }
        return itemsList;
    }

    public int getmHeadImgId() {
        return mHeadImgId;
    }

    public String getmDetail() {
        return mDetail;
    }

    public String getmCategory() {
        return mCategory;
    }

    public String getmWeek() {
        return mWeek;
    }

    public String getmDate() {
        return mDate;
    }

    public int getmTypeIconId() {
        return mTypeIconId;
    }

    public float getmMoney() {
        return mMoney;
    }

    public String getmName() {
        return mName;
    }
}
