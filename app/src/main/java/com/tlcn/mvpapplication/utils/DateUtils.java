package com.tlcn.mvpapplication.utils;

import android.content.Context;

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
    public static String getTimeAgo(Context context, Date date) {
        final long SECOND_MILLIS = 1000;
        final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final long DAY_MILLIS = 24 * HOUR_MILLIS;
        final long WEEK_MILLIS = 7 * DAY_MILLIS;
        final long MONTH_MILLIS = 30 * DAY_MILLIS;
        final long YEAR_MILLIS = 365 * DAY_MILLIS;

        long time = date.getTime();
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Calendar cal = Calendar.getInstance();
        long now = cal.getTime().getTime();
        if (time > now || time <= 0) {
            return "";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Vừa xong";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " phút trước";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + "giờ trước";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Hôm qua";
        } else if (diff < 7 * DAY_MILLIS) {
            return diff / DAY_MILLIS + "ngày trước";
        } else if (diff < 30 * DAY_MILLIS) {
            return diff / WEEK_MILLIS + "tuần trước";
        } else if (diff < 365 * DAY_MILLIS) {
            return diff / MONTH_MILLIS + "tháng trước";
        } else {
            return diff / YEAR_MILLIS + "năm trước";
        }
    }

    public static String getCurrentDate() {
        DateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        serverDateFormat.setTimeZone(TimeZone.getDefault());
        serverDateFormat.setCalendar(new GregorianCalendar(TimeZone.getDefault()));
        Calendar cal = Calendar.getInstance();
        return serverDateFormat.format(cal.getTime());
    }
}
