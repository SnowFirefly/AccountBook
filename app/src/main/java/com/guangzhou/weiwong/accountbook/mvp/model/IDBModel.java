package com.guangzhou.weiwong.accountbook.mvp.model;

import android.content.Context;

import java.util.Date;
import java.util.List;

/**
 * Model层数据库操作接口
 * Created by Tower on 2016/6/8.
 */
public interface IDBModel {
    int TABLE_GROUP = 1, TABLE_PERSONAL = 2;

    void init(int table);   // 初始化

    void setTableType(int table);             // 选择需要操作的表

    <T> void addData(T bean);

    void deleteData(int idType, long id);

    void deleteAll();

    <T> T getDataById(long id);

    List getDataByGroupId(long groupId);

    List getDataByUserId(long userId);

    List getDataByTimeRange(long groupId, Date begin, Date end);

    List getAllData();

    boolean hasKey(long id);

    int getTotalCount();

    <T> void update(T bean);
}
