package com.guangzhou.weiwong.accountbook.mvp.presenter;

import android.util.Log;

import com.guangzhou.weiwong.accountbook.mvp.model.NetworkApiService;
import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tower on 2016/4/20.
 */
public class MainPresenter implements IPresenter {
    private final String TAG = getClass().getName();
    private NetworkApiService networkApiService;
    private IView iView;

    @Override
    public void onDetach() {

    }

    public MainPresenter(IView iView){
        networkApiService = new NetworkApiService();
        this.iView = iView;
    }

    public void createGroup(String userName, String password, String groupName){
        Log.d(TAG, "createGroup()");
        Observable<RegisterResult> observable = networkApiService.createGroup(userName, password, groupName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterResult>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError():" + e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterResult user) {
                        Log.d(TAG, "onNext()");
                        Log.d(TAG, user.toString());
                        iView.onRegisterResult(user);
                    }
                });
    }
}
