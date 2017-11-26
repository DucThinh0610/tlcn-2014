package com.tlcn.mvpapplication.model.direction;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.utils.DecodePolyLine;

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
}
