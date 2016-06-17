package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.mvp.view.IView;

/**
 * Created by Tower on 2016/6/4.
 */
public class GroupPresenter implements IGroupPresenter {
    @Override
    public void addGroup() {

    }

    @Override
    public void deleteGroup() {

    }

    @Override
    public void addMember() {

    }

    @Override
    public void deleteMember() {

    }

    @Override
    public void onAttach(IView iView) {

    }

    @Override
    public void onDetach() {

    }
//    public void createGroup(String userName, String password, String groupName){
//        Log.d(TAG, "createGroup()");
//        Observable<Result> observable = network.createGroup(userName, password, groupName);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subs  cribe(new Subscriber<Result>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "onCompleted()");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError():" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Result result) {
//                        Log.d(TAG, "onNext()");
//                        Log.d(TAG, result.toString());
//                        iView.onRegisterResult(result);
//                    }
//                });
//    }
}
