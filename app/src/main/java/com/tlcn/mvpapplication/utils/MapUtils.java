package com.tlcn.mvpapplication.utils;

import android.content.Context;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.tlcn.mvpapplication.R;

public class MapUtils {
    public static CircleOptions circleOptions(Context context, LatLng latLng, int radius) {
        return new CircleOptions()
                .center(latLng)
                .radius(radius)
                .strokeColor(context.getResources().getColor(R.color.color_transparent))
                .fillColor(context.getResources().getColor(R.color.color_bound_transparent));
    }
    public static final LatLngBounds HCM = new LatLngBounds(new LatLng(10.748822, 106.594357), new LatLng(10.902364, 106.839401));
}
