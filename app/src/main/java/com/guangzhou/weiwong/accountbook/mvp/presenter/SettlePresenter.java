package com.guangzhou.weiwong.accountbook.mvp.presenter;

import com.guangzhou.weiwong.accountbook.mvp.model.IDBModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IDownloadModel;
import com.guangzhou.weiwong.accountbook.mvp.model.IUploadModel;
import com.guangzhou.weiwong.accountbook.mvp.view.IView;

/**
 * Created by Tower on 2016/6/4.
 */
public class SettlePresenter implements ISettlePresenter {
    private IUploadModel iUploadModel;
    private IDownloadModel iDownloadModel;
    private IDBModel idbModel;

    public SettlePresenter(IUploadModel iUploadModel, IDownloadModel iDownloadModel, IDBModel idbModel) {
        this.iUploadModel = iUploadModel;
        this.iDownloadModel = iDownloadModel;
        this.idbModel = idbModel;
        idbModel.init(IDBModel.TABLE_PERSONAL);
    }

    @Override
    public void getSettleData() {

    }

    @Override
    public void setSettleData() {

    }

    @Override
    public void onAttach(IView iView) {

    }

    @Override
    public void onDetach() {

    }
}
