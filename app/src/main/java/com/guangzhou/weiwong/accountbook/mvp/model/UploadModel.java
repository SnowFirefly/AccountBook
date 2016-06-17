package com.guangzhou.weiwong.accountbook.mvp.model;

import javax.inject.Inject;

/**
 * Created by Tower on 2016/6/3.
 */
public class UploadModel implements IUploadModel{

    public UploadModel() {
    }

    @Override
    public void login(String account, String password) {

    }

    @Override
    public void register(String account, String password, String email) {

    }

    @Override
    public void commitOneRecord(int groudId, int categoryId, String detail, float price, String time) {

    }

    @Override
    public void setProfileInfo() {

    }

    @Override
    public void setSettleState() {

    }

    @Override
    public void addGroup(String groupName) {

    }

    @Override
    public void deleteGroup() {

    }

    @Override
    public void addMember(String account, int groupId) {

    }

    @Override
    public void deleteMember(String account, int groupId) {

    }
}
