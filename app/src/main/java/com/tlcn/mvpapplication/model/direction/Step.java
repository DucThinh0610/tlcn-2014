package com.tlcn.mvpapplication.model.direction;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.model.CustomLatLng;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.utils.DecodePolyLine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ducthinh on 17/09/2017.
 */

public class Step implements Serializable {

    @SerializedName("distance")
    @Expose
    private Distance distance;
    @SerializedName("duration")
    @Expose
    private Duration duration;
    @SerializedName("end_location")
    @Expose
    private Location endLocation;
    @SerializedName("start_location")
    @Expose
    private Location startLocation;
    @SerializedName("polyline")
    @Expose
    private Polyline polyline;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("html_instructions")
    @Expose
    private String description;
    @SerializedName("maneuver")
    @Expose
    private String maneuver;

    private List<CustomLatLng> customLatLng;

    private List<Locations> locations = new ArrayList<>();

    public List<Locations> getLocations() {
        return locations;
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }

    public void addLocation(Locations locations) {
        if (locations != null)
            this.locations.add(locations);
    }

    public boolean checkAddLocation(Locations item) {
        if (locations != null) {
            if (locations.size() == 0) {
                locations.add(item);
                return true;
            } else {
                for (Locations lct : locations) {
                    if (Objects.equals(lct.getId(), item.getId()))
                        return false;
                }
                locations.add(item);
                return true;
            }
        } else return false;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LatLng> getPoints() {
        if (this.polyline == null)
            return new ArrayList<>();
        return DecodePolyLine.decodePolyLine(this.polyline.getPoints());
    }

    public int getCurrentLevel() {
        int result = 0;
        if (locations.size() != 0) {
            for (Locations location : locations) {
                result += location.getCurrent_level();
            }
        }
        return result;
    }

    public void createMarkPlace() {
        customLatLng = new ArrayList<>();
        for (LatLng latLng : DecodePolyLine.decodePolyLine(polyline.getPoints())) {
            customLatLng.add(new CustomLatLng(latLng));
        }
    }

    public List<CustomLatLng> getCustomLatLng() {
        return customLatLng;
    }

    public int getCountLocationPassed() {
        int count = 0;
        for (CustomLatLng customLatLng : customLatLng) {
            if (customLatLng.getState() == 1)
                count++;
        }
        return count;
    }

    public List<LatLng> getLocationNonePass() {
        List<LatLng> temp = new ArrayList<>();
        for (CustomLatLng customLatLng : customLatLng) {
            if (customLatLng.getState() == 0)
                temp.add(customLatLng.getLatLng());
        }
        return temp;
    }
}
