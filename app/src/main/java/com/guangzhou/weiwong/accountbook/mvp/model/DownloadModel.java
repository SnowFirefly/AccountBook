package com.guangzhou.weiwong.accountbook.mvp.model;

import com.guangzhou.weiwong.accountbook.mvp.MyApplication;
import com.guangzhou.weiwong.accountbook.mvp.model.Result.Result;
import com.guangzhou.weiwong.accountbook.utils.MyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import rx.Observable;

/**
 * Created by Tower on 2016/5/30.
 */
public class DownloadModel implements IDownloadModel {
    @Override
    public Observable<Result> getGroupData() {
        return null;
    }

    @Override
    public Observable<Result> getCurWeekData(Date mon, Date sun) {
        MyLog.d(this, "getCurWeekData(" + mon + ", " + sun + ")");
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectTotal = new JSONObject();
        try {
            jsonObject.put("mon", mon);

            jsonObjectTotal.put("user", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyLog.i(this, jsonObjectTotal.toString());
        Observable<Result> resultObservable = MyApplication.getApiService().getCurWeekData(jsonObjectTotal.toString());
        return resultObservable;
    }

    @Override
    public Observable<Result> getOneDayData(Date date) {
        return null;
    }

    @Override
    public Observable<Result> getProfileInfo(String account, String password) {
        return null;
    }

    @Override
    public Observable<Result> getAllData4Per(long userId) {
        return null;
    }

    @Override
    public Observable<Result> getDailyData(Date begin, Date end) {
        return null;
    }

    @Override
    public Observable<Result> getStatisticData(int type, long groupId, long userId) {
        return null;
    }

    @Override
    public Observable<Result> getSettleData() {
        return null;
    }

    @Override
    public Observable<Result> getCategories() {
        return null;
    }

}
