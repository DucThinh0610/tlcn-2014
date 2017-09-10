package com.tlcn.mvpapplication.model;

import java.io.Serializable;

/**
 * Created by ducthinh on 10/09/2017.
 */

public class Place implements Serializable {
    private CharSequence placeId;
    private CharSequence placeName;
    private CharSequence placeDetail;

    public Place(CharSequence placeId, CharSequence placeName, CharSequence placeDetail) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeDetail = placeDetail;
    }

    public CharSequence getPlaceId() {
        return placeId;
    }

    public CharSequence getPlaceName() {
        return placeName;
    }

    public CharSequence getPlaceDetail() {
        return placeDetail;
    }
}
