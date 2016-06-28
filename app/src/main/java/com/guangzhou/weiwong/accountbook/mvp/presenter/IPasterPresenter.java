package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.mvp.model.bean.Record;

import java.util.Date;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IPasterPresenter extends IPresenter {
    void getOneDayData(Date date);       // 获取一天的收支数据

    void setOneDayData(Record record);       // 提交一天的收支数据
}
