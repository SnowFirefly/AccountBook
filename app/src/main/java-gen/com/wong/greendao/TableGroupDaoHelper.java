package com.wong.greendao;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 数据库操作（分组表）
 * Created by Tower on 2016/6/8.
 */
public class TableGroupDaoHelper implements MyDaoHelperInterface {
    private TableRecordGroupDao groupDao;
    QueryBuilder queryBuilder;
    DeleteQuery deleteQuery;
    Query query;

    public TableGroupDaoHelper(TableRecordGroupDao groupDao) {
        this.groupDao = groupDao;
        queryBuilder = groupDao.queryBuilder();
    }

    @Override
    public <T> void addData(T bean) {
        if (groupDao != null && bean != null) {
            groupDao.insertOrReplace((TableRecordGroup) bean);
        }
    }

    @Override
    public void deleteData(int idType, long id) {
        if (groupDao == null){
            return;
        }
        switch (idType) {
            case MyDaoHelperInterface.ID_TABLE:
                groupDao.deleteByKey(id);
                break;
            case MyDaoHelperInterface.ID_USER:
                deleteQuery = queryBuilder.where(TableRecordGroupDao.Properties.Buyer_id
                    .eq(id)).buildDelete();
                deleteQuery.executeDeleteWithoutDetachingEntities();
                break;
            case MyDaoHelperInterface.ID_GROUP:
                deleteQuery = queryBuilder.where(TableRecordGroupDao.Properties.Group_id
                        .eq(id)).buildDelete();
                deleteQuery.executeDeleteWithoutDetachingEntities();
                break;
            default:    break;
        }
    }

    @Override
    public void deleteAll() {
        if (groupDao != null){
            groupDao.deleteAll();
        }
    }

    @Override
    public TableRecordGroup getDataById(long id) {
        if (groupDao == null) {
            return null;
        }
        return groupDao.load(id);
    }

    @Override
    public List getDataByGroupId(long groupId) {
        Query query = queryBuilder.where(TableRecordGroupDao.Properties.Group_id.eq(groupId)).build();
        return query.list();
    }

    @Override
    public List getDataByUserId(long userId) {
        Query query = queryBuilder.where(TableRecordGroupDao.Properties.Buyer_id.eq(userId)).build();
        return query.list();
    }

    @Override
    public List getDataByTimeRange(long groupId, Date begin, Date end) {
        if (groupDao == null) {
            return null;
        }
        if (query == null) {
            query = queryBuilder.where(TableRecordGroupDao.Properties.Group_id.eq(groupId),
                    queryBuilder.and(TableRecordGroupDao.Properties.Buy_time.ge(begin),
                            TableRecordGroupDao.Properties.Buy_time.le(end)))
                    .orderDesc(TableRecordGroupDao.Properties.Buy_time)
                    .build();
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        } else {
            query.setParameter(0, groupId);
            query.setParameter(1, begin);
            query.setParameter(2, end);
        }
        return query.list();
    }

    @Override
    public List getAllData() {
        if (groupDao != null){
            return groupDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(long id) {
        if (groupDao == null) {
            return false;
        }
        queryBuilder.where(TableRecordGroupDao.Properties.Buyer_id.eq(id));
        long count = queryBuilder.buildCount().count();
        return count > 0;
    }

    @Override
    public int getTotalCount() {
        if (groupDao == null) {
            return 0;
        }
        return (int) queryBuilder.buildCount().count();
    }

    @Override
    public <T> void update(T bean) {
        groupDao.update((TableRecordGroup) bean);
    }

//    public List queryByName(String name) {
//        Query query = groupDao.queryBuilder()
//                .where(TableRecordGroupDao.Properties.Buyer.eq(name))
//                .orderAsc(TableRecordGroupDao.Properties.Id)
//                .build();
//        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
//        QueryBuilder.LOG_SQL = true;
//        QueryBuilder.LOG_VALUES = true;
//        return query.list();
//    }

//    public void testQueryBy() {
//        List joes = userBeanDao.queryBuilder()
//                .where(UserBeanDao.Properties.Phone.eq("Joe"))
//                .orderAsc(UserBeanDao.Properties.Phone)
//                .list();
//
//        QueryBuilder<UserBean> qb = userBeanDao.queryBuilder();
//        qb.where(qb.or(UserBeanDao.Properties.Phone.gt(10698.85),
//                qb.and(UserBeanDao.Properties.Phone.eq("id"),
//                        UserBeanDao.Properties.Phone.eq("xx"))));
//
//        qb.orderAsc(UserBeanDao.Properties.Id);// 排序依据
//        qb.list();
//        qb.where(qb.and(Properties.CityId.eq(cityId), Properties.InfoType.eq(HBContant.CITYINFO_IR)));
//    }
}
