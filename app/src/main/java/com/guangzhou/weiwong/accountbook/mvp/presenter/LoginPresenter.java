package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerNetworkComponent;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.Network;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.User;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.utils.ApiException;
import com.guangzhou.weiwong.accountbook.utils.ErrorHandler;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Tower on 2016/4/25.
 */
public class LoginPresenter implements ILoginPresenter {
    private final String TAG = getClass().getSimpleName();
    @Inject Network network;
    private IUploadModel iUploadModel;
    private IDownloadModel iDownloadModel;
    private IView iView;

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {

    }

    public LoginPresenter() {
        DaggerNetworkComponent.builder().build().inject(this);
    }

    public LoginPresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel) {
        this.iUploadModel = iUploadModel;
        MyLog.e(this, "iUploadModel: " + iUploadModel);
        DaggerNetworkComponent.builder().build().inject(this);
        this.iDownloadModel = iDownloadModel;
        MyLog.e(this, "iDownloadModel: " + iDownloadModel);
    }

    @Override
    public void login(String login_name, String password){
        MyLog.d(TAG, "login(" + login_name + ", " + password + ")");
        Observable<Result> observable = network.login(login_name, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        MyLog.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.d(TAG, "onError():" + e.getMessage());
                        ApiException apiException = ErrorHandler.handle(e);
                        MyLog.i(TAG, "apiException: " + apiException);
                        MyLog.i(TAG, "apiException.code: " + apiException.getCode());
                        MyLog.i(TAG, "apiException.msg: " + apiException.getMsg());
                        iView.onError(apiException.getMsg());
                    }

                    @Override
                    public void onNext(Result result) {
                        MyLog.d(TAG, "onNext()");
                        MyLog.i(TAG, result.toString());
//                        User user = (User) result.getData();        // convert fail

                        iView.onSignResult(result.toString());

                        String newStr = result.getData().toString().replace("null", "unknown");
                        MyLog.i(TAG, newStr);
                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                .create();
//                        User user = gson.fromJson(result.getData().toString(), User.class);
                        User user = gson.fromJson(gson.toJson(result.getData()), User.class);
                        MyLog.i(TAG, "json: " + gson.toJson(result.getData()));
                        MyLog.i(TAG, "user: " + user.toString());
                        MyLog.i(TAG, "id: " + user.getId());
                        MyLog.i(TAG, "name: " + user.getName());
                        MyLog.i(TAG, "password: " + user.getPassword());
                        MyLog.i(TAG, "email: " + user.getEmail());
                        MyLog.i(TAG, "lastLoginTime: " + user.getLastLoginTime());
                        MyLog.i(TAG, "createTime: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreateTime()));
                        MyLog.i(TAG, "type: " + user.getType());
                    }
                });
    }

    public void login2(String login_name, String password){
        MyLog.d(TAG, "login2(" + login_name + ", " + password +")");
        Observable<Response<Result>> observable = network.login2(login_name, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Func1<Result, Boolean>() {
//                    @Override
//                    public Boolean call(Result user) {
//                        return user.getAck().equals("fail");
//                    }
//                })
                .subscribe(new Subscriber<Response<Result>>() {
                    @Override
                    public void onCompleted() {
                        MyLog.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.d(TAG, "onError(): " + e.getMessage());
                    }

                    @Override
                    public void onNext(Response<Result> user) {
                        MyLog.d(TAG, "onNext()");
                        MyLog.i(TAG, user.body().toString());
                        Gson gson = new GsonBuilder().create();
                        if (user.body().getAck().equals("success")) {
                            User data = gson.fromJson(user.body().getData().toString(), User.class);
                            MyLog.i(LoginPresenter.this, data.toString());
                        }
                        iView.onSignResult(user.body().toString());
                        if (user.errorBody() != null) {
                            MyLog.d(TAG, user.errorBody().toString());
                        }
                    }
                });
    }

    @Override
    public void testLogin(String userName, String password){
        MyLog.d(TAG, "testLogin()");
        Call<Result> call = network.testLogin(userName, password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    MyLog.i(TAG, response.body().toString());
                    MyLog.e(TAG, response.errorBody().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                MyLog.e(TAG, t.getMessage());
            }
        });
    }

    private boolean ackSuccess(Result result){
        String ack = result.getAck();
        return ack.equals("success");
    }

}
