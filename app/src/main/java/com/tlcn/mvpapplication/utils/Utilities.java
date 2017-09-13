package com.tlcn.mvpapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tlcn.mvpapplication.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tskil on 8/23/2017.
 */

public class Utilities {
    public static final float MIN_MAP_ZOOM = 0f;
    public static final float MAX_MAP_ZOOM = 23f;
    public static final float DEFAULT_MAP_ZOOM = 15f;

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

    public static String getTimeAgo(Context context,Date startDate) {
        Date endDate = Calendar.getInstance().getTime();
        String result = "";
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthsInMilli = daysInMilli * 28;
        long yearsInMilli = monthsInMilli * 12;

        long weeksInMilli = daysInMilli * 7;

        long elapsedYears = different / yearsInMilli;

        long elapsedMonths = different / monthsInMilli;

        long elapsedWeeks = different / weeksInMilli;

        long elapsedDays = different / daysInMilli;

        long elapsedHours = different / hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;

        if (elapsedYears >= 1 ){
            result = elapsedYears+" "+context.getString(R.string.year)+" "+context.getString(R.string.ago);
        }
        else if(elapsedMonths >= 1){
            result = elapsedMonths+" "+context.getString(R.string.month)+" "+context.getString(R.string.ago);
        }
        else if(elapsedWeeks >=1){
            result = elapsedWeeks+" "+context.getString(R.string.week)+" "+context.getString(R.string.ago);
        }
        else if(elapsedDays >= 1){
            result = elapsedDays+" "+context.getString(R.string.day)+" "+context.getString(R.string.ago);
        }
        else if(elapsedHours >= 1){
            result = elapsedHours+" "+context.getString(R.string.hour)+" "+context.getString(R.string.ago);
        }
        else if(elapsedMinutes >= 1){
            result = elapsedMinutes+" "+context.getString(R.string.minute)+" "+context.getString(R.string.ago);
        }
        else{
            result = context.getString(R.string.now);
        }
        return result;
    }
}
