package com.wong.greendao;

import android.content.Context;

/**
 * 辅助类,管理DaoMater和每个表的DAO
 * Created by Tower on 2016/6/8.
 */
public class DBHelper {
    private static DBHelper instance;
    private static Context mContext;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private TableRecordGroupDao tableRecordGroupDao;
    private TableRecordPersonalDao tableRecordPersonalDao;

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            MyOpenHelper myOpenHelper = new MyOpenHelper(context.getApplicationContext(),
                    "accountbook_db", null);
            daoMaster = new DaoMaster(myOpenHelper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    private DBHelper() {}

    public static DBHelper getInstance() {
        return instance;
    }

    public static void init(Context context) {
        mContext = context;
        instance = new DBHelper();
        DaoSession daoSession = getDaoSession(mContext);
        instance.setTableRecordGroupDao(daoSession.getTableRecordGroupDao());
        instance.setTableRecordPersonalDao(daoSession.getTableRecordPersonalDao());
    }

    public TableRecordGroupDao getTableRecordGroupDao() {
        return tableRecordGroupDao;
    }

    public void setTableRecordGroupDao(TableRecordGroupDao tableRecordGroupDao) {
        this.tableRecordGroupDao = tableRecordGroupDao;
    }

    public TableRecordPersonalDao getTableRecordPersonalDao() {
        return tableRecordPersonalDao;
    }

    public void setTableRecordPersonalDao(TableRecordPersonalDao tableRecordPersonalDao) {
        this.tableRecordPersonalDao = tableRecordPersonalDao;
    }
}
