package com.tlcn.mvpapplication.utils;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.PolyUtil;
import com.tlcn.mvpapplication.R;

public class MapUtils {
    public static CircleOptions circleOptions(Context context, LatLng latLng, int radius) {
        return new CircleOptions()
                .center(latLng)
                .radius(radius)
                .strokeColor(context.getResources().getColor(R.color.color_transparent))
                .fillColor(context.getResources().getColor(R.color.color_bound_transparent));
    }

    public static float distanceBetweenTwoPoint(LatLng firstPoint, LatLng secondPoint) {
        Location location1 = new Location("Point A");
        location1.setLatitude(firstPoint.latitude);
        location1.setLongitude(firstPoint.longitude);

        Location location2 = new Location("Point B");
        location2.setLatitude(secondPoint.latitude);
        location2.setLongitude(secondPoint.longitude);
        return location1.distanceTo(location2);
    }

    public static float distanceFromPointToPolyline(LatLng startLocation, LatLng endLocation, LatLng point) {
        float edgeA = distanceBetweenTwoPoint(point, startLocation);
        float edgeB = distanceBetweenTwoPoint(startLocation, endLocation);
        float edgeC = distanceBetweenTwoPoint(point, endLocation);
        if (!isBelongToLine(edgeA, edgeB, edgeC)) {
            float p = (edgeA + edgeB + edgeC) / 2;
            return (float) (2 * (Math.sqrt((p * (p - edgeA) * (p - edgeB) * (p - edgeC)) / edgeB)));
        } else return -1;
    }

    private static boolean isBelongToLine(float edgeA, float edgeB, float edgeC) {
        return findCosine(edgeC, edgeB, edgeA) < 0 ||
                findCosine(edgeA, edgeB, edgeC) < 0;
    }

    private static float findCosine(float x, float edgeX1, float edgeX2) {
        return (float) ((Math.pow(edgeX1, 2) + Math.pow(edgeX2, 2) - Math.pow(x, 2)) / (2 * edgeX1 * edgeX2));
    }

    public static final LatLngBounds HCM = new LatLngBounds(new LatLng(10.748822, 106.594357), new LatLng(10.902364, 106.839401));
}
