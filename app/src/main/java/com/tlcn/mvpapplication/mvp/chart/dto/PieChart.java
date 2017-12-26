package com.tlcn.mvpapplication.mvp.chart.dto;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.tlcn.mvpapplication.model.chart.ChartData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PieChart implements IChartDto {

    private PieData dataByHour, dataByDay;

    //type ==0 : hour of day ; type==1 day of week
    private int type = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private List<ChartData> mList;

    public PieChart(List<ChartData> mList) {
        this.mList = mList;
        String[] mPartiesHour = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23"
        };
        dataByHour = createData(mPartiesHour, 0);
        String[] mPartiesDay = new String[]{
                "Chủ nhật", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"
        };
        dataByDay = createData(mPartiesDay, 1);
    }

    public PieData getDataHour() {
        return dataByHour;
    }

    public PieData getDataDay() {
        return dataByDay;
    }

    private PieData createData(String[] listPart, int type) {
        List<pieItem> pieItems = new ArrayList<>();
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (ChartData chartData : mList) {
            pieItem item = new pieItem(listPart[type == 0 ? chartData.getHour() : chartData.getDay()], type == 0 ? chartData.getHour() : chartData.getDay());
            if (!pieItems.contains(item)) {
                pieItems.add(item);
            } else {
                pieItems.get(pieItems.indexOf(item)).count++;
            }
        }
        Collections.sort(pieItems, new Comparator<pieItem>() {
            @Override
            public int compare(pieItem pieItem, pieItem t1) {
                Integer type1 = pieItem.type;
                Integer type2 = t1.type;
                return type1.compareTo(type2);
            }
        });
        Log.d("DAta", new Gson().toJson(pieItems));
        for (int i = 0; i < pieItems.size(); i++) {
            entries.add(new PieEntry((float) ((pieItems.get(i).count * 100) + 100 / mList.size()),
                    pieItems.get(i).part, null));
        }
        PieDataSet dataSet = new PieDataSet(entries, type == 0 ? "Khung giờ" : "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(10f);
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        return data;
    }

    static class pieItem {
        String part;
        int type;
        int count;

        pieItem(String part, int type) {
            this.part = part;
            this.type = type;
            count = 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            } else {
                pieItem item = (PieChart.pieItem) obj;
                return item.type == this.type;
            }
        }
    }
}
