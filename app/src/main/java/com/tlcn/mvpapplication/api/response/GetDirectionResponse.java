package com.tlcn.mvpapplication.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.model.direction.Route;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ducthinh on 17/09/2017.
 */

public class GetDirectionResponse extends BaseResponse implements Serializable {
    @SerializedName("routes")
    @Expose
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
