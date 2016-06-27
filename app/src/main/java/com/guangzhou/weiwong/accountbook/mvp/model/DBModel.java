package com.guangzhou.weiwong.accountbook.mvp.model;

import android.text.TextUtils;

import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.wong.greendao.DBHelper;
import com.wong.greendao.MyDaoHelperInterface;
import com.wong.greendao.TableGroupDaoHelper;
import com.wong.greendao.TablePersonalDaoHelper;
import com.wong.greendao.TableRecordGroupDao;
import com.wong.greendao.TableRecordPersonalDao;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by Tower on 2016/6/8.
 */
public class DBModel implements IDBModel {
    private int table = TABLE_PERSONAL;
//    private Context mContext;
//    private DBHelper dbHelper;
//    private TableRecordGroupDao groupDao;
//    private TableRecordPersonalDao personalDao;
    private MyDaoHelperInterface groupDaoHelper, personalDaoHelper;

    @Override
    public void init(int table) {
        this.table = table;
        DBHelper dbHelper = DBHelper.getInstance();
        TableRecordGroupDao groupDao = dbHelper.getTableRecordGroupDao();
        TableRecordPersonalDao personalDao = dbHelper.getTableRecordPersonalDao();
        groupDaoHelper = new TableGroupDaoHelper(groupDao);
        personalDaoHelper = new TablePersonalDaoHelper(personalDao);
    }

    @Override
    public void setTableType(int table) {
        this.table = table;
    }

    @Override
    public <T> void addData(T bean) {
        if (table == TABLE_GROUP) {
            groupDaoHelper.addData(bean);
        } else if (table == TABLE_PERSONAL) {
            personalDaoHelper.addData(bean);
        }
    }

    @Override
    public void deleteData(int idType, long id) {
        if (table == TABLE_GROUP) {
            groupDaoHelper.deleteData(idType, id);
        } else if (table == TABLE_PERSONAL) {
            personalDaoHelper.deleteData(idType, id);
        }
    }

    @Override
    public void deleteAll() {
        if (table == TABLE_GROUP) {
            groupDaoHelper.deleteAll();
        } else if (table == TABLE_PERSONAL) {
            personalDaoHelper.deleteAll();
        }
    }

    @Override
    public <T> T getDataById(long id) {
        if (table == TABLE_GROUP) {
            return groupDaoHelper.getDataById(id);
        } else if (table == TABLE_PERSONAL) {
            return personalDaoHelper.getDataById(id);
        }
        return null;
    }

    @Override
    public List getDataByGroupId(long groupId) {
        if (table == TABLE_GROUP) {
            return groupDaoHelper.getDataByGroupId(groupId);
        } else {
            return null;
        }
    }

    @Override
    public List getDataByUserId(long userId) {
        if (table == TABLE_GROUP) {
            return groupDaoHelper.getDataByUserId(userId);
        } else if (table == TABLE_PERSONAL) {
            return personalDaoHelper.getDataByUserId(userId);
        }
        return null;
    }

    @Override
    public List getDataByTimeRange(long groupId, Date begin, Date end) {
        if (table == TABLE_GROUP) {
            return groupDaoHelper.getDataByTimeRange(groupId, begin, end);
        } else if (table == TABLE_PERSONAL) {
            return personalDaoHelper.getDataByTimeRange(groupId, begin, end);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if (table == TABLE_GROUP) {
            return groupDaoHelper.getAllData();
        } else if (table == TABLE_PERSONAL) {
            return personalDaoHelper.getAllData();
        }
        return null;
    }

    @Override
    public boolean hasKey(long id) {
        if (table == TABLE_GROUP) {
            return groupDaoHelper.hasKey(id);
        } else if (table == TABLE_PERSONAL) {
            return personalDaoHelper.hasKey(id);
        }
        return false;
    }

    @Override
    public int getTotalCount() {
        if (table == TABLE_GROUP) {
            return groupDaoHelper.getTotalCount();
        } else if (table == TABLE_PERSONAL) {
            return personalDaoHelper.getTotalCount();
        }
        return 0;
    }

    @Override
    public <T> void update(T bean) {
        if (table == TABLE_GROUP) {
            groupDaoHelper.update(bean);
        } else if (table == TABLE_PERSONAL) {
            personalDaoHelper.update(bean);
        }
    }
}
