package com.guangzhou.weiwong.accountbook.mvp.model.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan on 2016/5/21.
 */
public class MemberItem {
    private String headIconUrl;
    private String name;

    public MemberItem(String headIconUrl, String name) {
        this.headIconUrl = headIconUrl;
        this.name = name;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public String getName() {
        return name;
    }

    public static List<MemberItem> getFakeItems(){
        List<MemberItem> memberItems = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            memberItems.add(new MemberItem("", "Luffy" + i));
        }
        return memberItems;
    }
}
