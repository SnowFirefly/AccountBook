package com.guangzhou;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.wong.greendao");

        // schema2.enableActiveEntitiesByDefault();
        // schema2.enableKeepSectionsByDefault();

        addGroupTable(schema);
        addPersonalTable(schema);

        new DaoGenerator().generateAll(schema, "E:/Projects/Github/AccountBook/app/src/main/java-gen");
    }

    /**
     * 添加收支记录表（分组）
     * @param schema
     */
    private static void addGroupTable(Schema schema) {
        Entity groupTable = schema.addEntity("TableRecordGroup");
        groupTable.addIdProperty();                             // 记录ID
        groupTable.addLongProperty("cloud_id").notNull();       // 记录ID（服务端）
        groupTable.addStringProperty("detail").notNull();       // 记录详情
        groupTable.addFloatProperty("money").notNull();         // 总金额
        groupTable.addDateProperty("buy_time").notNull();       // 记录时间
        groupTable.addLongProperty("group_id");                 // 所在组ID
        groupTable.addStringProperty("group_name");             // 组名
        groupTable.addLongProperty("category_id");              // 消费种类ID
        groupTable.addStringProperty("category_name").notNull();// 消费种类名称
        groupTable.addLongProperty("buyer_id");                 // 支出（收入）人ID
        groupTable.addStringProperty("buyer").notNull();        // 支出（收入）人
        groupTable.addIntProperty("kind").notNull();            // 消费类型：支出0、收入1
        groupTable.addIntProperty("state").notNull();           // 状态：（默0）增1、改2、删3
        groupTable.addDateProperty("modify_time").notNull();    // 修改时间
    }

    /**
     * 添加收支记录表（个人）
     * @param schema
     */
    private static void addPersonalTable(Schema schema) {
        Entity personalTable = schema.addEntity("TableRecordPersonal");
        personalTable.addIdProperty();
        personalTable.addLongProperty("cloud_id").notNull();       // 记录ID（服务端）
        personalTable.addStringProperty("detail").notNull();
        personalTable.addFloatProperty("money").notNull();
        personalTable.addDateProperty("buy_time").notNull();
        personalTable.addLongProperty("category_id");
        personalTable.addStringProperty("category_name").notNull();
        personalTable.addLongProperty("buyer_id");
        personalTable.addStringProperty("buyer").notNull();
        personalTable.addIntProperty("kind").notNull();
        personalTable.addIntProperty("state").notNull();
        personalTable.addDateProperty("modify_time").notNull();
    }
}
