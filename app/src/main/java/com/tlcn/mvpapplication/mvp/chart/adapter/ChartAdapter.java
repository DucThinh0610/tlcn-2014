package com.tlcn.mvpapplication.mvp.chart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.chart.dto.PieChart;
import com.tlcn.mvpapplication.mvp.chart.dto.BarChart;
import com.tlcn.mvpapplication.mvp.chart.dto.IChartDto;
import com.tlcn.mvpapplication.mvp.chart.dto.LocationInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_INFO = 0, TYPE_HOUR = 1, TYPE_PIE = 2, ERROR = -1;
    private List<IChartDto> mList;
    private Context mContext;
    private OnItemClickListener mCallback;

    public ChartAdapter(List<IChartDto> mList, Context mContext, OnItemClickListener listener) {
        this.mList = mList;
        this.mContext = mContext;
        this.mCallback = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mList.get(position);
        if (item instanceof LocationInfo)
            return TYPE_INFO;
        else if (item instanceof BarChart)
            return TYPE_HOUR;
        else if (item instanceof PieChart)
            return TYPE_PIE;
        else
            return ERROR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_INFO)
            return new LocationInfoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_location_info, parent, false));
        else if (viewType == TYPE_HOUR)
            return new BarHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hour_chart, parent, false));
        else if (viewType == TYPE_PIE)
            return new PieHolder(LayoutInflater.from(mContext).inflate(R.layout.item_date_chart, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = mList.get(position);
        switch (getItemViewType(position)) {
            case TYPE_INFO:
                LocationInfoHolder locationInfoHolder = (LocationInfoHolder) holder;
                locationInfoHolder.setLocationInfo((LocationInfo) item);
                break;
            case TYPE_PIE:
                PieHolder dateHolder = (PieHolder) holder;
                PieChart pieChart = (PieChart) item;
                dateHolder.setData(pieChart.getType() == 0 ? pieChart.getDataHour() : pieChart.getDataDay());
                break;
        }
    }

    public class LocationInfoHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        @Bind(R.id.map_view)
        MapView mapView;
        GoogleMap mGoogleMap;
        private LocationInfo mLocationInfo;

        LocationInfoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mapView.onCreate(null);
            mapView.getMapAsync(this);
            mapView.onResume();
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;
            MapsInitializer.initialize(mContext);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
            mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
            if (mLocationInfo != null)
                updateMap();

        }

        void setLocationInfo(LocationInfo locationInfo) {
            mLocationInfo = locationInfo;
            if (mGoogleMap != null) {
                updateMap();
            }
        }

        private void updateMap() {
            mGoogleMap.clear();
            mGoogleMap.addMarker(new MarkerOptions().position(mLocationInfo.getLatLng()).
                    title(mContext.getString(R.string.traffic_jam_location_non_star))).showInfoWindow();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLocationInfo.getLatLng(), 18f);
            mGoogleMap.moveCamera(cameraUpdate);
        }
    }

    public class BarHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bar_chart)
        com.github.mikephil.charting.charts.BarChart barChart;

        public BarHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class PieHolder extends RecyclerView.ViewHolder implements OnChartValueSelectedListener, View.OnClickListener {
        @Bind(R.id.pie_chart)
        com.github.mikephil.charting.charts.PieChart mChart;
        @Bind(R.id.btn_popup)
        Button btnPopup;

        public PieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mChart.getDescription().setEnabled(false);
            mChart.setHoleRadius(52f);
            mChart.setTransparentCircleRadius(57f);
            mChart.setCenterText(generateCenterSpannableText());
            mChart.setCenterTextSize(9f);
            mChart.setUsePercentValues(true);
            mChart.setExtraOffsets(5, 10, 10, 10);

            Legend l = mChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);
            mChart.animateY(900);
            btnPopup.setOnClickListener(this);
        }

        private void setData(PieData data) {
            mChart.setData(data);

            mChart.highlightValues(null);

            mChart.invalidate();
        }

        private SpannableString generateCenterSpannableText() {

            SpannableString s = new SpannableString("Tỷ lệ kẹt xe.");
            s.setSpan(new RelativeSizeSpan(1.5f), 0, s.length(), 0);
            s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
            s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
            s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 0, s.length(), 0);
            return s;
        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {

        }

        @Override
        public void onNothingSelected() {

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_popup) {
                PopupMenu popupMenu = new PopupMenu(mContext, btnPopup, Gravity.END);
                popupMenu.getMenuInflater().inflate(R.menu.popup_pie_chart, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.hour:
                                mCallback.OnClickViewByHour();
                                break;
                            case R.id.day_of_week:
                                mCallback.OnClickViewByDayOfWeek();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {

        void OnClickViewByHour();

        void OnClickViewByDayOfWeek();
    }
}
