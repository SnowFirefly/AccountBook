package com.wong.greendao;

import java.util.Date;
import java.util.List;

/**
 * 数据库操作接口
 * Created by Tower on 2016/6/8.
 */
public interface MyDaoHelperInterface {
    int ID_TABLE = 0, ID_USER = 1, ID_GROUP = 2;

    // 增
    <T> void addData(T bean);

    // 删
    /**
     * 根据表项ID删除一条记录；根据支出人ID删除其所有记录；根据用户组ID删除该组所有记录。
     * @param idType　ID类型
     * @param id    ID号（Long）
     */
    void deleteData(int idType, long id);

    void deleteAll();

    // 查
    <T> T getDataById(long id);

    List getDataByGroupId(long groupId);

    List getDataByUserId(long userId);

    List getDataByTimeRange(long groupId, Date begin, Date end);

    List getAllData();

    boolean hasKey(long id);

    int getTotalCount();

    // 改
    <T> void update(T bean);
}
