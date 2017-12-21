package com.tlcn.mvpapplication.mvp.chart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.chart.dto.DateChart;
import com.tlcn.mvpapplication.mvp.chart.dto.HourChart;
import com.tlcn.mvpapplication.mvp.chart.dto.IChartDto;
import com.tlcn.mvpapplication.mvp.chart.dto.LocationInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_INFO = 0, TYPE_HOUR = 1, TYPE_DATE = 2, ERROR = -1;
    private List<IChartDto> mList;
    private Context mContext;

    public ChartAdapter(List<IChartDto> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mList.get(position);
        if (item instanceof LocationInfo)
            return TYPE_INFO;
        else if (item instanceof HourChart)
            return TYPE_HOUR;
        else if (item instanceof DateChart)
            return TYPE_DATE;
        else
            return ERROR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_INFO)
            return new LocationInfoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_location_info, parent, false));
        else if (viewType == TYPE_HOUR)
            return new HourHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hour_chart, parent, false));
        else if (viewType == TYPE_DATE)
            return new DateHolder(LayoutInflater.from(mContext).inflate(R.layout.item_date_chart, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class LocationInfoHolder extends RecyclerView.ViewHolder {

        public LocationInfoHolder(View itemView) {
            super(itemView);
        }
    }

    public class HourHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bar_chart)
        BarChart barChart;

        public HourHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DateHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.pie_chart)
        PieChart pieChart;

        public DateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
