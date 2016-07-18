package com.guangzhou.weiwong.accountbook.mvp.presenter;

import java.util.Date;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IDailyPresenter extends IPresenter {
    void getDailyData(long groupId, Date begin, Date end);        // 获取所有收支条目（默认当月）

    <T> T getOneTableRecord(long tableId);

    void deleteOneRecord(long tableId);

    void saveToSDCard();
}
