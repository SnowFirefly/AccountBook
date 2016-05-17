package com.guangzhou.weiwong.accountbook.mvp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tower on 2016/4/22.
 */
public class User {
    private String ack;
    private Object data;
    private String message;

    @Override
    public String toString() {
        return "{user: ack->" + ack + ", message->" + message + ", data->" + data + "}";
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

//    static class Data {
//        private String id;
//        private String name;
//        private String password;
//        private String email;
//        private Object lastLoginTime;
//        private String createTime;
//        private String type;
//
//        @Override
//        public String toString() {
//            return "{data: id->" + id + ", name->" + name + ", password->" + password
//                    + ", email->" + email + ", lastLoginTime->" + lastLoginTime
//                    + ", createTime->" + createTime + ", type->" + type;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public Object getLastLoginTime() {
//            return lastLoginTime;
//        }
//
//        public void setLastLoginTime(Object lastLoginTime) {
//            this.lastLoginTime = lastLoginTime;
//        }
//
//        public String getCreateTime() {
//            return createTime;
//        }
//
//        public void setCreateTime(String createTime) {
//            this.createTime = createTime;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//    }
}
