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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
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
        DateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
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

    public static String formatHour(String dateStr) {
        Date date = parseStringToDate(dateStr);
        String dateFormat = "HH:mm";
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

    public static Date getTodayEnd() {
        Date date = getDay();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);

        return cal.getTime();
    }

    public static Date getDay() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getFirstDateOfWeek(Date date) {
        int startDayOfWeek = Calendar.MONDAY;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        while (cal.get(Calendar.DAY_OF_WEEK) != startDayOfWeek) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }
        return cal.getTime();
    }

    public static String getDateFormat(Date date) {
        DateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        serverDateFormat.setTimeZone(TimeZone.getDefault());
        serverDateFormat.setCalendar(new GregorianCalendar(TimeZone.getDefault()));
        return serverDateFormat.format(date);
    }


    public static int getCurrentDayInMonth() {
        Date date = getDay();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMonth() {
        Date date = getDay();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getCurrentYear() {
        Date date = getDay();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getDayOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getYearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static Date createDateFromDMY(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        cal.set(Calendar.DATE, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        return cal.getTime();
    }

    public static String getMonth(int month) {
        String[] arrMonth = new String[]{
                "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        };
        return arrMonth[month];
    }

    public static boolean isValidDate(int day, int month, int year) {
        if (day < 1 || day > 31) {
            return false;
        }
        if (month < 0 || month > 11) {
            return false;
        }
        if (year < 0) {
            return false;
        }
        if (day < 29) {
            return true;
        }
        switch (month) {
            case 1:
                return day == 29 && isLeapYear(year);
            case 3:
            case 5:
            case 8:
            case 10:
                return day != 31;
            default:
                return true;
        }
    }

    private static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    public static Date getEndDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        return calendar.getTime();
    }

    public static String parseDateToString(Date dateEnd, Date dateStart) {
        if (dateEnd == null || dateStart == null) {
            return null;
        }
        SimpleDateFormat sdfResult = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return sdfResult.format(dateStart) + " - " + sdfResult.format(dateEnd);
    }

    public static String getHourFromStringDate(String dateStr) {
        Date date = parseStringToDate(dateStr);
        String dateFormat = "HH:mm";
        return formatDate(date, dateFormat);
    }
}
