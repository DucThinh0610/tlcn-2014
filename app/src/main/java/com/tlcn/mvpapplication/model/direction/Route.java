package com.tlcn.mvpapplication.model.direction;

import android.location.*;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
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
import java.util.ListIterator;
import java.util.Map;

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
        return "Số điểm kẹt hiện tại: " + String.valueOf(count);
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
            if (step.getCountLocationPassed() < step.getCustomLatLng().size())
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

    public void onChangeLocation(Location currentLocation) {
        boolean isBreak = false;
        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        for (int j = 0; j < getStepNonePass().size(); j++) {
            Step step = getStepNonePass().get(j);
//            if (step.getLocationNonePass().size() == 2) {
//                CustomLatLng startLocation = step.getLocationNonePass().get(0);
//                CustomLatLng endLocation = step.getLocationNonePass().get(1);
//                if (MapUtils.distanceBetweenTwoPoint(startLocation.getLatLng(), endLocation.getLatLng()) < 10) {
//                    startLocation.setState(1);
//                    endLocation.setState(1);
//                    callback.drawPolyline(startLocation.getLatLng(), endLocation.getLatLng());
//                }
//            } else {
//                for (int i = 1; i < step.getLocationNonePass().size(); i++) {
//                    CustomLatLng endLocation = step.getLocationNonePass().get(i);
//                    CustomLatLng startLocation = step.getLocationNonePass().get(i - 1);
//                    List<LatLng> polyline = new ArrayList<>();
//                    polyline.add(startLocation.getLatLng());
//                    polyline.add(endLocation.getLatLng());
//                    if (!PolyUtil.isLocationOnEdge(currentLatLng, polyline, true, 10.0D)) {
//                        if (MapUtils.distanceBetweenTwoPoint(currentLatLng, startLocation.getLatLng()) >
//                                MapUtils.distanceBetweenTwoPoint(currentLatLng, endLocation.getLatLng())) {
//                            startLocation.setState(1);
//                            callback.drawPolyline(startLocation.getLatLng(), endLocation.getLatLng());
//                        }
//                        else {
//                            isBreak=true;
//                            break;
//                        }
//                    } else {
//                        startLocation.setState(1);
//                        step.getCustomLatLng().add(0, new CustomLatLng(0, currentLatLng));
//                        callback.drawPolyline(startLocation.getLatLng(), currentLatLng);
//                        isBreak = true;
//                        break;
//                    }
//                }
//                if (isBreak)
//                    break;
//            }
            if (PolyUtil.isLocationOnEdge(currentLatLng, step.getLatLngNonePass(), true, 5.0D)) {
                Log.d("Route", "True");
                for (int i = 0; i < step.getLocationNonePass().size(); i++) {
                    CustomLatLng startLocation = step.getLocationNonePass().get(i);
                    CustomLatLng endLocation;
                    if (i + 1 < step.getLocationNonePass().size())
                        endLocation = step.getLocationNonePass().get(i + 1);
                }
            } else
                Log.d("Route", "False");
        }
    }

    public void addCurrentLocation(LatLng latLng) {
        Step step = getSteps().get(0);
        step.getCustomLatLng().add(0, new CustomLatLng(latLng));
    }

    public interface OnChangeLocationListener {

        void drawPolyline(LatLng latLngStart, LatLng latLngEnd);
    }
}
