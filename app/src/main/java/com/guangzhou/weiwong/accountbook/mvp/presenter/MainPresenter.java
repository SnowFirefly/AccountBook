package com.guangzhou.weiwong.accountbook.mvp.presenter;

import android.util.Log;

import com.guangzhou.weiwong.accountbook.mvp.model.Network;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Tower on 2016/4/20.
 */
public class MainPresenter implements IMainPresenter {
    private final String TAG = getClass().getName();
    private Network network;
    private IView iView;

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {

    }

    public MainPresenter(){
        network = new Network();
    }

    public void createGroup(String userName, String password, String groupName){
        Log.d(TAG, "createGroup()");
        Observable<Result> observable = network.createGroup(userName, password, groupName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError():" + e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {
                        Log.d(TAG, "onNext()");
                        Log.d(TAG, result.toString());
                        iView.onRegisterResult(result);
                    }
                });
    }

    @Override
    public void getCurWeekData() {

    }

    @Override
    public void getGroupData() {

    }
}
