package com.tlcn.mvpapplication.utils;

import android.content.Context;
import android.util.Log;

import com.tlcn.mvpapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ducthinh on 19/09/2017.
 */

public class DateUtils {
    public static String getTimeAgo(Context context, Date startDate) {
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

        if (elapsedYears >= 1) {
            result = elapsedYears + " " + context.getString(R.string.year) + " " + context.getString(R.string.ago);
        } else if (elapsedMonths >= 1) {
            result = elapsedMonths + " " + context.getString(R.string.month) + " " + context.getString(R.string.ago);
        } else if (elapsedWeeks >= 1) {
            result = elapsedWeeks + " " + context.getString(R.string.week) + " " + context.getString(R.string.ago);
        } else if (elapsedDays >= 1) {
            result = elapsedDays + " " + context.getString(R.string.day) + " " + context.getString(R.string.ago);
        } else if (elapsedHours >= 1) {
            result = elapsedHours + " " + context.getString(R.string.hour) + " " + context.getString(R.string.ago);
        } else if (elapsedMinutes >= 1) {
            result = elapsedMinutes + " " + context.getString(R.string.minute) + " " + context.getString(R.string.ago);
        } else {
            result = context.getString(R.string.now);
        }
        return result;
    }

    public static Date parseStringToDate(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            Log.e("Obuut", "ParseException: " + e.getMessage());
            e.printStackTrace();

        }
        return null;
    }

    public static String formatDateToString(String sDate) {
        if (sDate == null || sDate.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        try {
            Date date = sdf.parse(sDate);
            SimpleDateFormat sdfResult = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy", Locale.US);
            return sdfResult.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentDate() {
        DateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        serverDateFormat.setTimeZone(TimeZone.getDefault());
        serverDateFormat.setCalendar(new GregorianCalendar(TimeZone.getDefault()));
        Calendar cal = Calendar.getInstance();
        return serverDateFormat.format(cal.getTime());
    }

    public static String formatFullDate(String dateStr) {
        Date date = parseStringToDate(dateStr);
        String dateFormat = "dd/MM/yyyy";
        dateFormat += "   HH:mm";
        return formatDate(date, dateFormat);
    }

    private static String formatDate(Date date, String desFormat) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateResultFormat = new SimpleDateFormat(desFormat, Locale.US);
        dateResultFormat.setTimeZone(TimeZone.getDefault());
        return dateResultFormat.format(date);
    }
}
