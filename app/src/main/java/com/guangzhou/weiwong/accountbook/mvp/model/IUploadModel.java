package com.guangzhou.weiwong.accountbook.mvp.model;

/**
 * Created by Tower on 2016/5/30.
 */
public interface IUploadModel {

    // 登录
    void login(String account, String password);

    // 注册
    void register(String account, String password, String email);

    // 提交一条记录
    void commitOneRecord(int groudId, int categoryId, String detail, float price, String time);

    // 修改个人信息
    void setProfileInfo();

    // 设置结算状态
    void setSettleState();

    // 添加分组
    void addGroup(String groupName);

    // 删除分组
    void deleteGroup();

    // 添加组员
    void addMember(String account, int groupId);

    // 删除组员
    void deleteMember(String account, int groupId);
}
