package com.guangzhou.weiwong.accountbook.mvp.model;

import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;

import java.util.Date;

import rx.Observable;

/**
 * Created by Tower on 2016/4/18.
 */
public interface IDownloadModel {

    Observable<Result> getGroupData();        // 获取分组信息

    Observable<Result> getCurWeekData(Date mon, Date sun);      // 获取当前周的收支数据

    Observable<Result> getOneDayData(Date date);       // 获取一天的收支数据

    Observable<Result> getProfileInfo(String account, String password);       // 获取个人信息

    Observable<Result> getAllData4Per(long userId);      // 获取个人所有收支情况

    Observable<Result> getDailyData(Date begin, Date end);        // 获取所有收支条目（默认当月）

    // 待定
    Observable<Result> getStatisticData(int type, long groupId, long userId);    // 获取统计数据

    Observable<Result> getSettleData();       // 获取结算数据

    Observable<Result> getCategories();       // 获取分类
}
