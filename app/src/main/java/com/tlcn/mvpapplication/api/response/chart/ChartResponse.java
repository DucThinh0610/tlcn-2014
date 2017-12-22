package com.tlcn.mvpapplication.api.response.chart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.model.chart.ChartInfo;

import java.io.Serializable;

public class ChartResponse implements Serializable {
    @SerializedName("body")
    @Expose
    private ChartInfo chartInfo;

    public ChartInfo getChartInfo() {
        return chartInfo;
    }
}
