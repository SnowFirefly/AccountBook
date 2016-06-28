package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.bean.BusData;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;
import com.guangzhou.weiwong.accountbook.utils.Const;
import com.guangzhou.weiwong.accountbook.utils.TimeUtil;
import com.wong.greendao.TableRecordPersonal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Tower on 2016/6/4.
 */
public class ChartPresenter implements IChartPresenter {
    private IUploadModel iUploadModel;
    private IDownloadModel iDownloadModel;
    private IDBModel idbModel;
    private IView iView;

    public ChartPresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel, IDBModel idbModel) {
        this.iUploadModel = iUploadModel;
        this.iDownloadModel = iDownloadModel;
        this.idbModel = idbModel;
        idbModel.init(IDBModel.TABLE_PERSONAL);
    }

    @Override
    public void getStatisticData(int dataType, Date begin, Date end) {
        getLocal(dataType, begin, end);
    }

    private void getLocal(int dataType, Date begin, Date end) {
        List<TableRecordPersonal> personals = idbModel.getDataByTimeRange(0, begin, end);
        GregorianCalendar calendar = new GregorianCalendar();
        float[] data = null;
        switch (dataType) {
            case Const.DATE_TYPE_DAY:
                data = new float[31];
                for (TableRecordPersonal personal: personals) {
                    if (personal.getKind() == Const.MONEY_KIND_EARN) continue;
                    Date date = personal.getBuy_time();
                    calendar.setTime(date);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    data[day - 1] += personal.getMoney();
                }
                break;
            case Const.DATE_TYPE_MONTH:
                data = new float[12];
                for (TableRecordPersonal personal: personals) {
                    if (personal.getKind() == Const.MONEY_KIND_EARN) continue;
                    Date date = personal.getBuy_time();
                    calendar.setTime(date);
                    int month = calendar.get(Calendar.MONTH);
                    data[month] += personal.getMoney();
                }
                break;
            case Const.DATE_TYPE_YEAR:
                data = new float[TimeUtil.getYear() + 1 - TimeUtil.BEGIN_YEAR];
                for (TableRecordPersonal personal: personals) {
                    if (personal.getKind() == Const.MONEY_KIND_EARN) continue;
                    Date date = personal.getBuy_time();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    data[year - TimeUtil.BEGIN_YEAR] += personal.getMoney();
                }
                break;
            case Const.DATA_TYPE_CATE:
                data = new float[5];
                for (TableRecordPersonal personal: personals) {
                    if (personal.getKind() == Const.MONEY_KIND_EARN) continue;
                    data[personal.getCategory_id().intValue()] += personal.getMoney();
                }
                break;
        }
        if (data != null) {
            iView.onLoadResult(IView.STATISTIC_DATA, new BusData(dataType, data));
        }
    }

    @Override
    public void onAttach(IView iView) {
        this.iView = iView;
    }

    @Override
    public void onDetach() {

    }
}
