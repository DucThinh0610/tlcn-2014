package com.tlcn.mvpapplication.api.response.chart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.chart.ChartInfo;

import java.io.Serializable;

public class ChartResponse extends BaseResponse implements Serializable {
    @SerializedName("body")
    @Expose
    private ChartInfo chartInfo;

    public ChartInfo getChartInfo() {
        return chartInfo;
    }
}
