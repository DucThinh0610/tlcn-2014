package com.tlcn.mvpapplication.api.request.chart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.utils.DateUtils;

import java.util.Date;

public class ChartRequest {
    @SerializedName("start_date")
    @Expose
    Date startDate;
    @SerializedName("end_date")
    @Expose
    Date endDate;
    @SerializedName("id")
    @Expose
    String idLocation;

    public ChartRequest(Date startDate, Date endDate, String idLocation) {
        if (endDate == null || startDate == null) {
            this.endDate = DateUtils.getTodayEnd();
            this.startDate = DateUtils.getFirstDateOfWeek(DateUtils.getDay());
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.idLocation = idLocation;
    }
}
