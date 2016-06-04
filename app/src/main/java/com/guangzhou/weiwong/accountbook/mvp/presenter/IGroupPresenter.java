package com.guangzhou.weiwong.accountbook.mvp.presenter;

/**
 * Created by Tower on 2016/6/4.
 */
public interface IGroupPresenter extends IPresenter {
    void addGroup();        // 添加分组

    void deleteGroup();     // 删除分组

    void addMember();       // 添加组员

    void deleteMember();    // 删除组员
}
