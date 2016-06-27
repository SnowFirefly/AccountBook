package com.guangzhou.weiwong.accountbook.mvp.model.bean;

import java.util.Date;

/**
 * MVP中V的bean
 * Created by Tower on 2016/6/18.
 */
public class Record {
    private long id;
    private Date date;
    private String detail;
    private float money;
    private int kind;
    private long cate;
    private String cateName;
    private long userId;
    private String userName;
    private long groupId;
    private String groupName;
    private int state;

    public Record(long id, Date date, String groupName, long groupId, String userName,
                  long userId, String cateName, long cate, int kind, float money, String detail, int state) {
        this.id = id;
        this.date = date;
        this.groupName = groupName;
        this.groupId = groupId;
        this.userName = userName;
        this.userId = userId;
        this.cateName = cateName;
        this.cate = cate;
        this.kind = kind;
        this.money = money;
        this.detail = detail;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public long getCate() {
        return cate;
    }

    public void setCate(long cate) {
        this.cate = cate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
