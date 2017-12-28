package com.tlcn.mvpapplication.mvp.chart.dto;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.chart.ChartData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LocationInfo implements IChartDto {

    private Locations locations;

    private String timeTrafficJamMax = "";

    private String countNew = "0";

    private double ratingOverview = 0;

    public LocationInfo(Locations locations, List<ChartData> chartData) {
        this.locations = locations;
        calculate(chartData);
    }

    private void calculate(List<ChartData> chartData) {
        if (chartData.size() != 0) {
            int count = 0;
            double level = 0;
            ChartData max = Collections.max(chartData, new Comparator<ChartData>() {
                @Override
                public int compare(ChartData o1, ChartData o2) {
                    if (o1.getLevel() == o2.getLevel()) {
                        return 0;
                    } else if (o1.getLevel() > o2.getLevel()) {
                        return -1;
                    } else if (o1.getLevel() < o2.getLevel()) {
                        return 1;
                    }
                    return 0;
                }
            });
            for (ChartData item : chartData) {
                count++;
                level += item.getLevel();
            }
            ratingOverview = count == 0 ? 0 : level / count;
            countNew = String.valueOf(count) + " bài";
            timeTrafficJamMax = max.getHour() + " - " + String.valueOf(max.getHour() + 1) + " giờ";
        }
    }

    public LatLng getLatLng() {
        return new LatLng(locations.getLat(), locations.getLng());
    }

    public String getName() {
        return locations.getTitle().replaceAll("Kẹt xe tại ", "");
    }

    public String getTimeTrafficJamMax() {
        return timeTrafficJamMax;
    }

    public String getCountNew() {
        return countNew;
    }

    public double getRatingOverview() {
        return ratingOverview;
    }
}
