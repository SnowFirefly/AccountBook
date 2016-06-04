package com.guangzhou.weiwong.accountbook.mvp.model.Result;


import java.util.Date;

/**
 * 用户信息Bean
 * Created by Tower on 2016/6/2.
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private Date last_login_time;
    private Date create_time;
    private String type;

    @Override
    public String toString() {
        return "{data: id->" + id + ", name->" + name + ", password->" + password
                + ", email->" + email + ", lastLoginTime->" + last_login_time
                + ", createTime->" + create_time + ", type->" + type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLoginTime() {
        return last_login_time;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.last_login_time = lastLoginTime;
    }

    public Date getCreateTime() {
        return create_time;
    }

    public void setCreateTime(Date createTime) {
        this.create_time = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
