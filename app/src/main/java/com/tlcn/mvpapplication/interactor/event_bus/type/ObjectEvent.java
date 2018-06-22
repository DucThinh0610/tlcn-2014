package com.tlcn.mvpapplication.interactor.event_bus.type;

import android.util.Log;

import com.google.gson.Gson;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.Post;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by ducthinh on 07/02/2018.
 */

public class ObjectEvent {
    private static final String TAG = ObjectEvent.class.getSimpleName();
    private JSONObject jsonObject;
    private String keyId;

    public ObjectEvent(String keyId, Object... args) {
//        this.jsonObject = (JSONObject) args[0];
        String s = args[0].toString();
        try {
            jsonObject = new JSONObject(s);
            Log.d("resultSocket" + TAG, jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            jsonObject = null;
        }
        this.keyId = keyId;
    }

    public String getKeyId() {
        return keyId;
    }

    public Locations getSocketLocation() {
        try {
            if (jsonObject != null)
                return new Gson().fromJson(jsonObject.toString(), Locations.class);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return null;
    }

    public Post getSocketPost(){
        try {
            if (jsonObject!=null){
                return new Gson().fromJson(jsonObject.toString(),Post.class);
            }
        }catch (Exception e){
            Log.d(TAG,e.getMessage());
        }
        return null;
    }
}
