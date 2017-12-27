package com.tlcn.mvpapplication.mvp.chart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatRatingBar;
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
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
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
    private static final int TYPE_INFO = 0, TYPE_HOUR = 1, TYPE_PIE = 2, ERROR = -1, TYPE_LINE = 3;
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
        else if (item instanceof com.tlcn.mvpapplication.mvp.chart.dto.LineChart)
            return TYPE_LINE;
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
        else if (viewType == TYPE_LINE)
            return new LineHolder(LayoutInflater.from(mContext).inflate(R.layout.item_line_chart, parent, false));
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
            case TYPE_LINE:
                LineHolder lineHolder = (LineHolder) holder;
                com.tlcn.mvpapplication.mvp.chart.dto.LineChart lineChart = (com.tlcn.mvpapplication.mvp.chart.dto.LineChart) item;
                lineHolder.setData(lineChart.getType() == 0 ? lineChart.getHourData() : lineChart.getDayData());
                break;
        }
    }

    public class LocationInfoHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        @Bind(R.id.map_view)
        MapView mapView;
        @Bind(R.id.rtb_level)
        AppCompatRatingBar rtb;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.tv_time_max)
        TextView tvTime;
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
            rtb.setRating((float) locationInfo.getRatingOverview());
            tvCount.setText(locationInfo.getCountNew());
            tvTime.setText(locationInfo.getTimeTrafficJamMax());
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

        PieHolder(View itemView) {
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
                                mCallback.PieOnClickViewByHour();
                                break;
                            case R.id.day_of_week:
                                mCallback.PieOnClickViewByDayOfWeek();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        }
    }

    class LineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.line_chart)
        LineChart lineChart;
        @Bind(R.id.btn_popup)
        Button btnPopup;
        @Bind(R.id.btn_zoom)
        Button btnZoom;

        LineHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnPopup.setOnClickListener(this);
            btnZoom.setOnClickListener(this);
            lineChart.setDrawGridBackground(false);
            lineChart.getDescription().setEnabled(false);
            lineChart.setTouchEnabled(true);
            lineChart.setDragEnabled(true);
            lineChart.setScaleEnabled(true);
            lineChart.setPinchZoom(true);

            // x-axis limit line
//            LimitLine llXAxis = new LimitLine(1f, "Index 10");
//            llXAxis.setLineWidth(4f);
//            llXAxis.enableDashedLine(10f, 10f, 0f);
//            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            llXAxis.setTextSize(6f);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.enableGridDashedLine(5f, 10f, 0f);
            LimitLine ll1 = new LimitLine(4.5f, "Cao");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(5f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(5f);

            LimitLine ll2 = new LimitLine(3.5f, "Thấp");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(5f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(5f);

            LimitLine ll3 = new LimitLine(3.5f, "Trung bình");
            ll3.setLineWidth(4f);
            ll3.enableDashedLine(5f, 10f, 0f);
            ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll3.setTextSize(5f);

            YAxis leftAxis = lineChart.getAxisLeft();
            leftAxis.removeAllLimitLines();
            leftAxis.addLimitLine(ll1);
            leftAxis.addLimitLine(ll2);
            leftAxis.setAxisMaximum(6f);
            leftAxis.setAxisMinimum(0f);
            leftAxis.enableGridDashedLine(5f, 10f, 0f);
            leftAxis.setDrawZeroLine(false);
            leftAxis.setDrawLimitLinesBehindData(true);

            lineChart.getAxisRight().setEnabled(false);

            lineChart.animateX(2500);
            Legend l = lineChart.getLegend();
            l.setForm(Legend.LegendForm.LINE);

        }

        public void setData(LineData lineData) {
            lineChart.setData(lineData);

            lineChart.highlightValues(null);

            lineChart.invalidate();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_popup:
                    PopupMenu popupMenu = new PopupMenu(mContext, btnPopup, Gravity.END);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_pie_chart, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.hour:
                                    mCallback.LineOnClickViewByHour();
                                    break;
                                case R.id.day_of_week:
                                    mCallback.LineOnClickViewByDayOfWeek();
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                    break;
                case R.id.btn_zoom:
                    mCallback.OnClickZoomLineChart();
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {

        void PieOnClickViewByHour();

        void PieOnClickViewByDayOfWeek();

        void LineOnClickViewByHour();

        void LineOnClickViewByDayOfWeek();

        void OnClickZoomLineChart();
    }
}
