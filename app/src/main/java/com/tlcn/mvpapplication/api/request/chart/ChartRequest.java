package com.tlcn.mvpapplication.api.request.chart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.utils.DateUtils;

import java.util.Date;

public class ChartRequest {
    @SerializedName("start_date")
    @Expose
    String startDate;
    @SerializedName("end_date")
    @Expose
    String endDate;

    public ChartRequest(Date startDate, Date endDate) {
        if (endDate == null || startDate == null) {
            this.endDate = DateUtils.getDateFormat(DateUtils.getTodayEnd());
            this.startDate = DateUtils.getDateFormat(DateUtils.getFirstDateOfWeek(DateUtils.getDay()));
        }
        this.startDate = DateUtils.getDateFormat(startDate);
        this.endDate = DateUtils.getDateFormat(endDate);
    }
}
