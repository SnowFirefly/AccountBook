package com.guangzhou.weiwong.accountbook.mvp.presenter;

import android.util.Log;

import com.guangzhou.weiwong.accountbook.mvp.model.NetworkApiService;
import com.guangzhou.weiwong.accountbook.mvp.model.data.RegisterResult;
import com.guangzhou.weiwong.accountbook.mvp.model.data.User;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;

import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tower on 2016/4/25.
 */
public class LoginPresenter implements ILoginPresenter {
    private final String TAG = getClass().getName();
    private NetworkApiService networkApiService;
    private IView iView;

    @Override
    public void onDetach() {

    }

    public LoginPresenter(IView iView){
        networkApiService = new NetworkApiService();
        this.iView = iView;
    }

    @Override
    public void login(String login_name, String password){
//        testLogin(login_name, password);
        Log.d(TAG, "login()");
        Observable<User> observable = networkApiService.login(login_name, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<User, Boolean>() {
                    @Override
                    public Boolean call(User user) {
                        return user.getAck().equals("fail");
                    }
                })
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                     public void onError(Throwable e) {
                        Log.d(TAG, "onError():" + e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext()");
                        Log.d(TAG, user.toString());
                        iView.onLoginResult(user);
                    }
                });
    }

    @Override
    public void testLogin(String userName, String password){
        Call<User> call = networkApiService.testLogin(userName, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    Log.i(TAG, response.body().toString());
                    Log.e(TAG, response.errorBody().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

}
