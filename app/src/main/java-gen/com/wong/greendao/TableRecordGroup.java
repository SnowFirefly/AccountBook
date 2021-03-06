package com.wong.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "TABLE_RECORD_GROUP".
 */
public class TableRecordGroup {

    private Long id;
    private long cloud_id;
    /** Not-null value. */
    private String detail;
    private float money;
    /** Not-null value. */
    private java.util.Date buy_time;
    private Long group_id;
    private String group_name;
    private Long category_id;
    /** Not-null value. */
    private String category_name;
    private Long buyer_id;
    /** Not-null value. */
    private String buyer;
    private int kind;
    private int state;
    /** Not-null value. */
    private java.util.Date modify_time;

    public TableRecordGroup() {
    }

    public TableRecordGroup(Long id) {
        this.id = id;
    }

    public TableRecordGroup(Long id, long cloud_id, String detail, float money, java.util.Date buy_time, Long group_id, String group_name, Long category_id, String category_name, Long buyer_id, String buyer, int kind, int state, java.util.Date modify_time) {
        this.id = id;
        this.cloud_id = cloud_id;
        this.detail = detail;
        this.money = money;
        this.buy_time = buy_time;
        this.group_id = group_id;
        this.group_name = group_name;
        this.category_id = category_id;
        this.category_name = category_name;
        this.buyer_id = buyer_id;
        this.buyer = buyer;
        this.kind = kind;
        this.state = state;
        this.modify_time = modify_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCloud_id() {
        return cloud_id;
    }

    public void setCloud_id(long cloud_id) {
        this.cloud_id = cloud_id;
    }

    /** Not-null value. */
    public String getDetail() {
        return detail;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    /** Not-null value. */
    public java.util.Date getBuy_time() {
        return buy_time;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setBuy_time(java.util.Date buy_time) {
        this.buy_time = buy_time;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    /** Not-null value. */
    public String getCategory_name() {
        return category_name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Long getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(Long buyer_id) {
        this.buyer_id = buyer_id;
    }

    /** Not-null value. */
    public String getBuyer() {
        return buyer;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /** Not-null value. */
    public java.util.Date getModify_time() {
        return modify_time;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setModify_time(java.util.Date modify_time) {
        this.modify_time = modify_time;
    }

}
