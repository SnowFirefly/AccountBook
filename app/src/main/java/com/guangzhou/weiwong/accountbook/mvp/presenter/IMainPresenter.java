package com.guangzhou.weiwong.accountbook.mvp.presenter;

import java.util.Date;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IMainPresenter extends IPresenter{
    void getCurWeekData(Date mon, Date sun);      // 获取当前周的收支数据

    void getGroupData();        // 获取分组信息

    void getLocalData(Date mon, Date sun);          // 获取本地数据库数据
}
