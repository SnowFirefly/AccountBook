package com.wong.greendao;

import java.sql.Date;
import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 数据库操作（个人表）
 * Created by Tower on 2016/6/8.
 */
public class TablePersonalDaoHelper implements MyDaoHelperInterface {
    private TableRecordPersonalDao personalDao;
    private QueryBuilder queryBuilder;
//    private DeleteQuery deleteQuery;
    private Query query;

    public TablePersonalDaoHelper(TableRecordPersonalDao personalDao) {
        this.personalDao = personalDao;
        queryBuilder = personalDao.queryBuilder();
    }


    @Override
    public <T> void addData(T bean) {
        if (personalDao != null && bean != null) {
            personalDao.insertOrReplace((TableRecordPersonal) bean);
        }
    }

    @Override
    public void deleteData(int idType, long id) {
        if (personalDao == null) {
            return;
        }
        switch (idType) {
            case MyDaoHelperInterface.ID_TABLE:
                personalDao.deleteByKey(id);
                break;
            case MyDaoHelperInterface.ID_USER:
                DeleteQuery deleteQuery = queryBuilder.where(TableRecordPersonalDao.Properties.Buyer_id
                        .eq(id)).buildDelete();
                deleteQuery.executeDeleteWithoutDetachingEntities();
                break;
            default:    break;
        }
    }

    @Override
    public void deleteAll() {
        if (personalDao != null) {
            personalDao.deleteAll();
        }
    }

    @Override
    public TableRecordPersonal getDataById(long id) {
        if (personalDao == null) {
            return null;
        }
        return personalDao.load(id);
    }

    @Override
    public List getDataByGroupId(long groupId) {
        return null;
    }

    @Override
    public List getDataByUserId(long userId) {
        Query query = queryBuilder.where(TableRecordPersonalDao.Properties.Buyer_id.eq(userId)).build();
        return query.list();
    }

    @Override
    public List getDataByTimeRange(long groupId, Date begin, Date end) {
        if (personalDao == null) {
            return null;
        }
        if (query == null) {
            query = queryBuilder.where(queryBuilder.and(TableRecordPersonalDao.Properties.Buy_time.ge(begin),
                            TableRecordPersonalDao.Properties.Buy_time.le(end)))
                    .orderDesc(TableRecordPersonalDao.Properties.Buy_time)
                    .build();
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        } else {
            query.setParameter(0, begin);
            query.setParameter(1, end);
        }
        return query.list();
    }

    @Override
    public List getAllData() {
        if (personalDao == null) {
            return null;
        }
        return personalDao.loadAll();
    }

    @Override
    public boolean hasKey(long id) {
        if (personalDao == null) {
            return false;
        }
        queryBuilder.where(TableRecordPersonalDao.Properties.Buyer_id.eq(id));
        long count = queryBuilder.buildCount().count();
        return count > 0;
    }

    @Override
    public int getTotalCount() {
        if (personalDao == null) {
            return 0;
        }
        return (int) queryBuilder.buildCount().count();
    }

    @Override
    public <T> void update(T bean) {
        personalDao.update((TableRecordPersonal) bean);
    }
}
