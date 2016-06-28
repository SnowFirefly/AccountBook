package com.guangzhou.weiwong.accountbook.mvp.presenter;

import java.util.Date;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IChartPresenter extends IPresenter {
    void getStatisticData(int dataType, Date begin, Date end);        // 获取统计数据
}
