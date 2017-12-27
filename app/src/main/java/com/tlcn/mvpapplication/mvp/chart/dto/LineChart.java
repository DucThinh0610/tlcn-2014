package com.tlcn.mvpapplication.mvp.chart.dto;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.model.chart.ChartData;

import java.util.ArrayList;
import java.util.List;

import static com.tlcn.mvpapplication.utils.KeyUtils.mPartiesHour;
import static com.tlcn.mvpapplication.utils.KeyUtils.mShortPartiesDay;

public class LineChart implements IChartDto {
    private int type = 0;
    private List<ChartData> mList;
    private LineData hourData;
    private LineData dayData;

    public LineChart(List<ChartData> chartData) {
        this.mList = chartData;
        hourData = createData(mPartiesHour, 0);
        dayData = createData(mShortPartiesDay, 1);
    }

    public LineData getHourData() {
        return hourData;
    }

    public LineData getDayData() {
        return dayData;
    }

    private LineData createData(String[] listPart, int type) {
        int sizeArray = type == 0 ? 24 : 7;
        LineItem[] lineItems = new LineItem[sizeArray];
        for (int i = 0; i < sizeArray; i++) {
            LineItem item = new LineItem(listPart[i], i);
            lineItems[i] = item;
        }
        for (ChartData chartData : mList) {
            int pos = type == 0 ? chartData.getHour() : chartData.getDay();
            lineItems[pos].level += chartData.getLevel();
            lineItems[pos].count++;
        }
        Log.d("Arrary", new Gson().toJson(lineItems));
        ArrayList<Entry> e1 = new ArrayList<>();
        for (int i = 0; i < lineItems.length; i++) {
            e1.add(new Entry(i, lineItems[i].count > 0 ? (float) lineItems[i].level / lineItems[i].count : 0));
        }
        LineDataSet d1 = new LineDataSet(e1, "Mức độ kẹt xe" + lineItems.length + "");

        d1.setDrawIcons(false);

        // set the line to be drawn like this "- - - - - -"
        d1.enableDashedLine(10f, 5f, 0f);
        d1.enableDashedHighlightLine(10f, 5f, 0f);
        d1.setColor(Color.BLACK);
        d1.setCircleColor(Color.BLACK);
        d1.setLineWidth(1f);
        d1.setCircleRadius(3f);
        d1.setDrawCircleHole(false);
        d1.setValueTextSize(9f);
        d1.setDrawFilled(true);
        d1.setFormLineWidth(1f);
        d1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        d1.setFormSize(15.f);

        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(App.getContext(), R.drawable.fade_red);
            d1.setFillDrawable(drawable);
        } else {
            d1.setFillColor(Color.BLACK);
        }
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        return new LineData(sets);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
