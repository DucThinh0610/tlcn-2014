package com.tlcn.mvpapplication.mvp.chart;

import com.tlcn.mvpapplication.base.BasePresenter;

public class ChartPresenter extends BasePresenter implements ChartContact.IPresenter {

    public void attachView(ChartContact.ChartView view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public ChartContact.ChartView getView() {
        return (ChartContact.ChartView) super.getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
