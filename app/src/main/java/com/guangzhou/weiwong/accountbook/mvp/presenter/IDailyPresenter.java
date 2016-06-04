package com.guangzhou.weiwong.accountbook.mvp.presenter;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IDailyPresenter extends IPresenter {
    void getDailyData();        // 获取所有收支条目（默认当月）
}
