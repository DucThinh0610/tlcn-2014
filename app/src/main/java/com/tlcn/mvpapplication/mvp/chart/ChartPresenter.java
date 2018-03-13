package com.tlcn.mvpapplication.mvp.chart;

import com.google.gson.Gson;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.chart.ChartRequest;
import com.tlcn.mvpapplication.api.response.chart.ChartResponse;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.mvp.chart.dto.LineChart;
import com.tlcn.mvpapplication.mvp.chart.dto.PieChart;
import com.tlcn.mvpapplication.mvp.chart.dto.IChartDto;
import com.tlcn.mvpapplication.mvp.chart.dto.LocationInfo;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartPresenter extends BasePresenter implements ChartContact.IPresenter {
    private List<IChartDto> iChartDtoList;
    private Date dateStart;
    private Date dateEnd;
    private String idLocation;

    List<IChartDto> getChartDtoList() {
        return iChartDtoList;
    }

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
        iChartDtoList = new ArrayList<>();
        dateEnd = DateUtils.getDay();
        dateStart = DateUtils.getFirstDateOfWeek(DateUtils.getDay());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getInfoChart() {
        if (!isViewAttached())
            return;
        getView().showLoading();
        getManager().getInfoChart(idLocation, new ChartRequest(dateStart, dateEnd),
                new ApiCallback<ChartResponse>() {
                    @Override
                    public void success(ChartResponse res) {
                        iChartDtoList.clear();
                        LogUtils.d("CHART", new Gson().toJson(res));
                        LocationInfo locationInfo = new LocationInfo(res.getChartInfo().getLocations(), res.getChartInfo().getChartData());
                        PieChart dateChart = new PieChart(res.getChartInfo().getChartData());
                        LineChart lineChart = new LineChart(res.getChartInfo().getChartData());
                        iChartDtoList.add(locationInfo);
                        iChartDtoList.add(dateChart);
                        iChartDtoList.add(lineChart);
                        getView().notifyChart();
                        getView().hideLoading();
                    }

                    @Override
                    public void failure(RestError error) {
                        getView().hideLoading();
                        getView().onFail(error.message);
                    }
                });
    }

    public List<IChartDto> getList() {
        return iChartDtoList;
    }

    Date getDateFrom() {
        return dateStart;
    }

    Date getDateEnd() {
        return dateEnd;
    }

    void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }
}
