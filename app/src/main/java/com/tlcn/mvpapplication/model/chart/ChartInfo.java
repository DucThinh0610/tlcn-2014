package com.tlcn.mvpapplication.model.chart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.model.Locations;

import java.io.Serializable;
import java.util.List;

public class ChartInfo implements Serializable{
    @SerializedName("location")
    @Expose
    Locations locations;
    @SerializedName("chart_data")
    @Expose
    List<ChartData> chartData;

    public Locations getLocations() {
        return locations;
    }

    public List<ChartData> getChartData() {
        return chartData;
    }
}
