package com.guangzhou.weiwong.accountbook.mvp.presenter;

import android.text.TextUtils;

import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.utils.Const;
import com.guangzhou.weiwong.accountbook.utils.MyLog;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.wong.greendao.TableRecordPersonal;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Tower on 2016/6/4.
 */
public class ProfilePresenter implements IProfilePresenter {
    private IUploadModel iUploadModel;
    private IDownloadModel iDownloadModel;
    private IDBModel idbModel;
    private IView iView;

    public ProfilePresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel, IDBModel idbModel) {
        this.iUploadModel = iUploadModel;
        this.iDownloadModel = iDownloadModel;
        this.idbModel = idbModel;
        idbModel.init(IDBModel.TABLE_PERSONAL);
    }

    @Override
    public void getProfileInfo() {

    }

    @Override
    public void setProfileInfo() {

    }

    @Override
    public void getAllData4Per() {
        getLocalAllData();

    }

    private void getLocalAllData() {
        List<TableRecordPersonal> personals = idbModel.getAllData();
        float[][] allData = new float[3][4];
        String begin = "", end = "";
        int days = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < personals.size(); i++) {
//            MyLog.i(this, "time: " + personals.get(i).getBuy_time());
            TableRecordPersonal personal = personals.get(i);
            if (i == 0) {
                end = sdf.format(personal.getBuy_time());
            } else if (i == (personals.size() - 1)) {
                begin = sdf.format(personal.getBuy_time());
            }

            if (personal.getKind() == Const.MONEY_KIND_SPEND) {
                allData[1][0] += personal.getMoney();
            } else if (personal.getKind() == Const.MONEY_KIND_EARN) {
                allData[1][1] += personal.getMoney();
            }
        }
        iView.onLoadResult(IView.TIME_RANGE, begin + " ~ " + end);
        if (!TextUtils.isEmpty(TimeUtil.getTwoDay(end, begin))) {
            days = Integer.parseInt(TimeUtil.getTwoDay(end, begin));
        }
        allData[1][2] = format(allData[1][0] * 30 / days, 1);
        allData[1][3] = format(allData[1][0] / days, 1);
        for (int i = 0; i < allData[0].length; i++) {
            allData[0][i] = allData[1][i] + allData[2][i];
        }
        iView.onLoadResult(IView.PERSONAL_DATA, allData);
        MyLog.i(this, "days: " + TimeUtil.getTwoDay(end, begin));
        MyLog.i(this, "spend: " + allData[1][0]);
        MyLog.i(this, "earn: " + allData[1][1]);
    }

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {

    }

    // 格式化百分比
    private float format(float value, int num) {
        int temp = (int) (value * Math.pow(10, num));
        return (float) (temp * 1.0 / Math.pow(10, num));
    }
}
