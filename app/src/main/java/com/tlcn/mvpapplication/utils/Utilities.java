package com.tlcn.mvpapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.Spanned;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by tskil on 8/23/2017.
 */

public class Utilities {


    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double meter = (valueResult / 1) * 1000;

        return meter;
    }

    public static LatLng calculationRadius(LatLng location) {
        double meters = 50;
        double coef = meters * 0.0000089;
        double new_lat = location.latitude + coef;
        double new_long = location.longitude + coef / Math.cos(location.latitude * 0.018);

        return new LatLng(new_lat, new_long);
    }

    public static String getAcronymNumber(int number) {
        String result = "";
        if (number / 1000000 >= 1) {
            result = (int) number / 1000000 + "m";
        } else if (number / 1000 >= 1) {
            result = (int) number / 1000 + "k";
        } else result = number + "";
        return result;
    }

    public static String getAcronymNumber(long number) {
        String result;
        if (number / 1000000 >= 1) {
            result = (int) number / 1000000 + "Tr";
        } else if (number / 1000 >= 1) {
            result = (int) number / 1000 + "N";
        } else result = number + "";
        return result;
    }

    public static String getDistanceString(int number) {
        String s = "";
        if (number >= 1000)
            s = (double) number / 1000 + " km";
        else
            s = number + " m";
        return s;
    }

    public static Spanned underlineText(String text) {
        return Html.fromHtml("<u>" + text + "</u>");
    }

    public static String createFileName() {
        DateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        serverDateFormat.setTimeZone(TimeZone.getDefault());
        serverDateFormat.setCalendar(new GregorianCalendar(TimeZone.getDefault()));
        Calendar cal = Calendar.getInstance();
        return serverDateFormat.format(cal.getTime()).replaceAll("-", "").replaceAll(":", "");
    }
}
