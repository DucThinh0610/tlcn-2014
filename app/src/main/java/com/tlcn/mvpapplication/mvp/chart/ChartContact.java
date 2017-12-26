package com.tlcn.mvpapplication.mvp.chart;

import com.tlcn.mvpapplication.base.IView;

public interface ChartContact {
    interface ChartView extends IView{
        void onFail(String message);

        void notifyChart();
    }

    interface IPresenter{

        void getInfoChart(String idLocation);
    }
}
