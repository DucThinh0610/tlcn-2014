package com.tlcn.mvpapplication.model.direction;

import android.location.*;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.PolyUtil;
import com.tlcn.mvpapplication.model.CustomLatLng;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.utils.DecodePolyLine;
import com.tlcn.mvpapplication.utils.MapUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Route implements Serializable {
    @SerializedName("bounds")
    @Expose
    private Bound bound;
    @SerializedName("legs")
    @Expose
    private List<Leg> leg;
    @SerializedName("overview_polyline")
    @Expose
    private Polyline polyline;
    @SerializedName("summary")
    @Expose
    private String summary;

    private OnChangeLocationListener callback;

    public void addOnChangeLocation(OnChangeLocationListener callback) {
        this.callback = callback;
    }

    private boolean isSelected = false;

    public Bound getBound() {
        return bound;
    }

    public List<Leg> getLeg() {
        return leg;
    }

    public List<LatLng> getPoints() {
        if (this.polyline == null)
            return new ArrayList<>();
        return DecodePolyLine.decodePolyLine(this.polyline.getPoints());
    }

    public String getTimeAndDistance() {
        int distance = 0, time = 0;
        for (Leg leg : this.getLeg()) {
            distance += leg.getDistance().getValue();
            time += leg.getDuration().getValue();
        }
        return String.valueOf(new DecimalFormat("###.#").format((float) distance / 1000)) + "Km - " +
                String.valueOf(time / 60) + "Phút";
    }

    public int getCurrentLevel() {
        int count = 0, current = 0;
        for (Step step : this.getLeg().get(0).getStep()) {
            if (step.getLocations().size() != 0) {
                count += step.getLocations().size();
                current += step.getCurrentLevel();
            }
        }
        return count != 0 ? current / count : 0;
    }

    public String getCountLocation() {
        int count = 0;
        for (Step step : this.getLeg().get(0).getStep()) {
            if (step.getLocations().size() != 0) {
                count += step.getLocations().size();
            }
        }
        return "Số điểm kẹt: " + String.valueOf(count);
    }

    public String getStartLocation() {
        return this.getLeg().get(0).getStartAddress();
    }

    public String getEndLocation() {
        return this.getLeg().get(0).getEndAddress();
    }

    public List<Step> getSteps() {
        return this.getLeg().get(0).getStep();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void createMarkPlace() {
        for (Step step : getSteps()) {
            step.createMarkPlace();
        }
    }

    public List<Step> getStepNonePass() {
        List<Step> tmp = new ArrayList<>();
        for (Step step : getSteps()) {
            if (step.getLocationNonePass().size() != 0)
                tmp.add(step);
        }
        return tmp;
    }

    public List<Locations> getListLocations() {
        List<Locations> temp = new ArrayList<>();
        for (Step step : this.getLeg().get(0).getStep()) {
            temp.addAll(step.getLocations());
        }
        return temp;
    }

    private List<LatLng> getLocationNonePass() {
        List<LatLng> latLngs = new ArrayList<>();
        for (Step step : getStepNonePass()) {
            latLngs.addAll(step.getLatLngNonePass());
        }
        return latLngs;
    }

    public void onChangeLocation(Location currentLocation) {
        boolean isBreak = false;
        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        while (getStepNonePass().size() != 0) {
            Step step = getStepNonePass().get(0);
            while (step.getLocationNonePass().size() != 0) {
                if (step.getEndLocation() != null) {
                    CustomLatLng startLocation = step.getStartLocation();
                    CustomLatLng endLocation = step.getEndLocation();
                    List<LatLng> polyline = new ArrayList<>();
                    polyline.add(startLocation.getLatLng());
                    polyline.add(endLocation.getLatLng());
                    if (PolyUtil.isLocationOnEdge(currentLatLng, getLocationNonePass(), true, 5.0D)) {
                        if (PolyUtil.isLocationOnEdge(currentLatLng, step.getLatLngNonePass(), true, 5.0D)) {
                            if (PolyUtil.isLocationOnEdge(currentLatLng, polyline, true, 5.0D)) {
                                callback.drawPolyline(currentLatLng, startLocation.getLatLng());
                                Location des = new Location("");
                                des.setLongitude(endLocation.getLatLng().longitude);
                                des.setLatitude(endLocation.getLatLng().latitude);
                                Location start = new Location("");
                                start.setLongitude(startLocation.getLatLng().longitude);
                                start.setLatitude(startLocation.getLatLng().latitude);
                                callback.changeBearing(MapUtils.getBearing(start, des));
                                startLocation.setState(1);
                                step.getCustomLatLng().add(0, new CustomLatLng(currentLatLng));
                                Log.d("Route", "Be Long to polyline");
                                isBreak = true;
                                break;
                            } else {
                                callback.drawPolyline(startLocation.getLatLng(), endLocation.getLatLng());
                                startLocation.setState(1);
                                Log.d("Route", "Start>End");
                            }
                        } else {
                            callback.drawPolyline(startLocation.getLatLng(), endLocation.getLatLng());
                            startLocation.setState(1);
                        }
                    } else {
                        Log.d("Route", "out of route");
                        isBreak = true;
                        break;
                    }
                } else if (step.getStartLocation() != null) {
                    step.getStartLocation().setState(1);
                } else {
                    Log.d("TAG", "Done");
                }
            }
            if (isBreak)
                break;

        }
    }

    public interface OnChangeLocationListener {

        void drawPolyline(LatLng latLngStart, LatLng latLngEnd);

        void changeBearing(float bearing);
    }
}
