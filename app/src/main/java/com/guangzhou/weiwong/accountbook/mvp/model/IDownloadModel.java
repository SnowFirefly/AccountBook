package com.guangzhou.weiwong.accountbook.mvp.model;

/**
 * Created by Tower on 2016/4/18.
 */
public interface IDownloadModel {

    void getGroupData();        // 获取分组信息

    void getCurWeekData();      // 获取当前周的收支数据

    void getOneDayData();       // 获取一天的收支数据

    // 获取个人信息
    void getProfileInfo(String account, String password);

    void getAllData4Per();      // 获取个人所有收支情况

    void getDailyData();        // 获取所有收支条目（默认当月）

    void getStatisticData();    // 获取统计数据

    void getSettleData();       // 获取结算数据

    void getCategories();       // 获取分类
}
