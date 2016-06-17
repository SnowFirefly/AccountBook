package com.wong.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.wong.greendao.TableRecordGroup;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TABLE_RECORD_GROUP".
*/
public class TableRecordGroupDao extends AbstractDao<TableRecordGroup, Long> {

    public static final String TABLENAME = "TABLE_RECORD_GROUP";

    /**
     * Properties of entity TableRecordGroup.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Cloud_id = new Property(1, long.class, "cloud_id", false, "CLOUD_ID");
        public final static Property Detail = new Property(2, String.class, "detail", false, "DETAIL");
        public final static Property Money = new Property(3, float.class, "money", false, "MONEY");
        public final static Property Buy_time = new Property(4, java.util.Date.class, "buy_time", false, "BUY_TIME");
        public final static Property Group_id = new Property(5, Long.class, "group_id", false, "GROUP_ID");
        public final static Property Group_name = new Property(6, String.class, "group_name", false, "GROUP_NAME");
        public final static Property Category_id = new Property(7, Long.class, "category_id", false, "CATEGORY_ID");
        public final static Property Category_name = new Property(8, String.class, "category_name", false, "CATEGORY_NAME");
        public final static Property Buyer_id = new Property(9, Long.class, "buyer_id", false, "BUYER_ID");
        public final static Property Buyer = new Property(10, String.class, "buyer", false, "BUYER");
        public final static Property Kind = new Property(11, int.class, "kind", false, "KIND");
        public final static Property State = new Property(12, int.class, "state", false, "STATE");
        public final static Property Modify_time = new Property(13, java.util.Date.class, "modify_time", false, "MODIFY_TIME");
    };


    public TableRecordGroupDao(DaoConfig config) {
        super(config);
    }
    
    public TableRecordGroupDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TABLE_RECORD_GROUP\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CLOUD_ID\" INTEGER NOT NULL ," + // 1: cloud_id
                "\"DETAIL\" TEXT NOT NULL ," + // 2: detail
                "\"MONEY\" REAL NOT NULL ," + // 3: money
                "\"BUY_TIME\" INTEGER NOT NULL ," + // 4: buy_time
                "\"GROUP_ID\" INTEGER," + // 5: group_id
                "\"GROUP_NAME\" TEXT," + // 6: group_name
                "\"CATEGORY_ID\" INTEGER," + // 7: category_id
                "\"CATEGORY_NAME\" TEXT NOT NULL ," + // 8: category_name
                "\"BUYER_ID\" INTEGER," + // 9: buyer_id
                "\"BUYER\" TEXT NOT NULL ," + // 10: buyer
                "\"KIND\" INTEGER NOT NULL ," + // 11: kind
                "\"STATE\" INTEGER NOT NULL ," + // 12: state
                "\"MODIFY_TIME\" INTEGER NOT NULL );"); // 13: modify_time
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TABLE_RECORD_GROUP\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableRecordGroup entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getCloud_id());
        stmt.bindString(3, entity.getDetail());
        stmt.bindDouble(4, entity.getMoney());
        stmt.bindLong(5, entity.getBuy_time().getTime());
 
        Long group_id = entity.getGroup_id();
        if (group_id != null) {
            stmt.bindLong(6, group_id);
        }
 
        String group_name = entity.getGroup_name();
        if (group_name != null) {
            stmt.bindString(7, group_name);
        }
 
        Long category_id = entity.getCategory_id();
        if (category_id != null) {
            stmt.bindLong(8, category_id);
        }
        stmt.bindString(9, entity.getCategory_name());
 
        Long buyer_id = entity.getBuyer_id();
        if (buyer_id != null) {
            stmt.bindLong(10, buyer_id);
        }
        stmt.bindString(11, entity.getBuyer());
        stmt.bindLong(12, entity.getKind());
        stmt.bindLong(13, entity.getState());
        stmt.bindLong(14, entity.getModify_time().getTime());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TableRecordGroup readEntity(Cursor cursor, int offset) {
        TableRecordGroup entity = new TableRecordGroup( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // cloud_id
            cursor.getString(offset + 2), // detail
            cursor.getFloat(offset + 3), // money
            new java.util.Date(cursor.getLong(offset + 4)), // buy_time
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // group_id
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // group_name
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // category_id
            cursor.getString(offset + 8), // category_name
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // buyer_id
            cursor.getString(offset + 10), // buyer
            cursor.getInt(offset + 11), // kind
            cursor.getInt(offset + 12), // state
            new java.util.Date(cursor.getLong(offset + 13)) // modify_time
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableRecordGroup entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCloud_id(cursor.getLong(offset + 1));
        entity.setDetail(cursor.getString(offset + 2));
        entity.setMoney(cursor.getFloat(offset + 3));
        entity.setBuy_time(new java.util.Date(cursor.getLong(offset + 4)));
        entity.setGroup_id(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setGroup_name(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCategory_id(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setCategory_name(cursor.getString(offset + 8));
        entity.setBuyer_id(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setBuyer(cursor.getString(offset + 10));
        entity.setKind(cursor.getInt(offset + 11));
        entity.setState(cursor.getInt(offset + 12));
        entity.setModify_time(new java.util.Date(cursor.getLong(offset + 13)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableRecordGroup entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TableRecordGroup entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
