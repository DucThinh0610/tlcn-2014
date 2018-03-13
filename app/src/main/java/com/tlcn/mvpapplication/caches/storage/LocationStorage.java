package com.tlcn.mvpapplication.caches.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tskil on 9/18/2017.
 */

public class LocationStorage implements ILocationStorage {
    private final String TAG = LocationStorage.class.getSimpleName();

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    private static final String PREF_NAME = "Location";

    //TODO: Key of PREF
    private static final String DISTANCE_FAVOURITE = "dis_fav";
    private static final String LAT_HOUSE = "lat_house";
    private static final String LOG_HOUSE = "log_house";
    private static final String LAT_WORK = "lat_work";
    private static final String LOG_WORK = "log_work";
    private static final String LAT_OTHER = "lat_other";
    private static final String LOG_OTHER = "log_other";
    private static final String ASK_EXTEND_BOUND = "ASK_EXTEND_BOUND";

    public LocationStorage(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public void createDistanceFavourite(int progress) {
        editor.putInt(DISTANCE_FAVOURITE, progress);
        editor.commit();
    }

    @Override
    public int getDistanceFavourite() {
        return pref.getInt(DISTANCE_FAVOURITE, 50);
    }

    @Override
    public void createHouseLocation(LatLng latLng) {
        editor.putString(LAT_HOUSE, String.valueOf(latLng.latitude));
        editor.putString(LOG_HOUSE, String.valueOf(latLng.longitude));
        editor.commit();
    }

    @Override
    public LatLng getHouseLocation() {
        double lat = Double.parseDouble(pref.getString(LAT_HOUSE, "0"));
        double log = Double.parseDouble(pref.getString(LOG_HOUSE, "0"));
        if (lat == 0 || log == 0) {
            return null;
        }
        return new LatLng(lat, log);
    }

    @Override
    public void removeHouseLocation() {
        editor.remove(LAT_HOUSE);
        editor.remove(LOG_HOUSE);
        editor.commit();
    }

    @Override
    public void createWorkLocation(LatLng latLng) {
        editor.putString(LAT_WORK, String.valueOf(latLng.latitude));
        editor.putString(LOG_WORK, String.valueOf(latLng.longitude));
        editor.commit();
    }

    @Override
    public LatLng getWorkLocation() {
        double lat = Double.parseDouble(pref.getString(LAT_WORK, "0"));
        double log = Double.parseDouble(pref.getString(LOG_WORK, "0"));
        if (lat == 0 || log == 0) {
            return null;
        }
        return new LatLng(lat, log);
    }

    @Override
    public void removeWorkLocation() {
        editor.remove(LAT_WORK);
        editor.remove(LOG_WORK);
        editor.commit();
    }

    @Override
    public void createOtherLocation(LatLng latLng) {
        editor.putString(LAT_OTHER, String.valueOf(latLng.latitude));
        editor.putString(LOG_OTHER, String.valueOf(latLng.longitude));
        editor.commit();
    }

    @Override
    public LatLng getOtherLocation() {
        double lat = Double.parseDouble(pref.getString(LAT_OTHER, "0"));
        double log = Double.parseDouble(pref.getString(LOG_OTHER, "0"));
        if (lat == 0 || log == 0) {
            return null;
        }
        return new LatLng(lat, log);
    }

    @Override
    public void removeOtherLocation() {
        editor.remove(LAT_OTHER);
        editor.remove(LOG_OTHER);
        editor.commit();
    }

}
