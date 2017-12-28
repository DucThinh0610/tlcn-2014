package com.tlcn.mvpapplication.utils;

/**
 * Created by tskil on 9/17/2017.
 */

public class KeyUtils {
    //TODO: KEY FOR SETTING GOOGLE_MAP
    public static final float MIN_MAP_ZOOM = 10f;
    public static final float MAX_MAP_ZOOM = 18f;
    public static final float DEFAULT_MAP_ZOOM = 15f;
    public static final float DEFAULT_MAP_ZOOM_DIRECTION = 18f;

    //TODO: KEY FOR FIREBASE REFERENCE
    public static final String NEWS = "news";
    public static final String IMAGES = "images";
    public static final String LOCATIONS = "locations";
    public static final String LIKE = "like";
    public static final String DISLIKE = "dislike";
    public static final String SAVE = "save";

    //TODO: KEY FOR VALIDATION
    public static final int DEFAULT_DISTANCE_TO_LOAD = 100;
    public static final int DEFAULT_NUMBER_MULTIPLY_DISTANCE = 20;

    //TODO: KEY FOR PUTING INTENT
    public static final String INTENT_KEY_ID = "id";
    public static final String INTENT_KEY_TITLE = "title";
    public static final String INTENT_KEY_LATITUDE = "latitude";
    public static final String INTENT_KEY_LONGITUDE = "longitude";
    public static final String KEY_INTENT_LOCATION = "KEY_INTENT_LOCATION";
    public static final String KEY_INTENT_DIRECTION = "KEY_INTENT_DIRECTION";
    public static final String KEY_INTENT_ID_LOCATION = "KEY_INTENT_ID_LOCATION";

    //TODO: KEY FOR OTHER
    public static final String KEY_DIRECTION_API = "AIzaSyCL8C2wURzDuzgF8VRSZ8GOLG0YEBT07Ig";

    public static final int REQUEST_PERMISSION_CAPTURE_IMAGE = 1;
    public static final int REQUEST_PERMISSION_READ_LIBRARY = 2;
    public static final int REQUEST_PERMISSION_WRITE_STORAGE = 3;
    public static final int REQUEST_CAPTURE_IMAGE = 4;
    public static final int REQUEST_READ_LIBRARY = 5;
    public static final int REQUEST_TAKE_PHOTO = 6;

    //TODO: Define a value for curren_level of location
    public static final double MIN_LEVEL = 3.0;
    public static final double MEDIUM_LEVEL = 4.5;
    public static final double MAX_LEVEL = 5.0;
    public static final double DEFAULT_DISTANCE_TO_POLYLINE = 50.0;


    /**
     * @return 1: min level
     * 2: medium level
     * 3: max level
     */
    public static int checkLevel(double currentLevel) {
        if (currentLevel <= MIN_LEVEL && currentLevel >= 0.0) {
            return 1;
        } else if (currentLevel > MIN_LEVEL && currentLevel <= MEDIUM_LEVEL) {
            return 2;
        } else return 3;
    }

    //todo: key of value current_level change
    public static int INCREASE_LEVEL = 2;
    public static int REDUCTION_LEVEL = 3;
    public static int NEW_LOCATION = 1;

    //data of chart
    public static String[] mPartiesHour = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23"
    };

    public static String[] mPartiesDay = new String[]{
            "Chủ nhật", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"
    };

    public static String[] mShortPartiesDay = new String[]{
            "CN", "T2", "T3", "T4", "T5", "T6", "T7"
    };
}
