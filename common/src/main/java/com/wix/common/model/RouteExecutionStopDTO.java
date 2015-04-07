package com.wix.common.model;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutionStopDTO {

    private String routeStopId;

    public RouteExecutionStopDTO() {

    }

    public RouteExecutionStopDTO(String routeStopId) {
        this.routeStopId = routeStopId;
    }

    public String getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(String routeStopId) {
        this.routeStopId = routeStopId;
    }

}
