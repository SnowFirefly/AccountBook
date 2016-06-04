package com.guangzhou.weiwong.accountbook.mvp.presenter;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IMainPresenter extends IPresenter{
    void getCurWeekData();      // 获取当前周的收支数据

    void getGroupData();        // 获取分组信息
}
