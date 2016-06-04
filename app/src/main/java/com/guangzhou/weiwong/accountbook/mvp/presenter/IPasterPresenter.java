package com.guangzhou.weiwong.accountbook.mvp.presenter;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IPasterPresenter extends IPresenter {
    void getOneDayData();       // 获取一天的收支数据

    void setOneDayData();       // 提交一天的收支数据
}
