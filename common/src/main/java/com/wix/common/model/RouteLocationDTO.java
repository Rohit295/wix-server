package com.wix.common.model;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteLocationDTO {

    private String id;

    private LocationDTO location;

    private RouteStopDTO routeStop;

    public RouteLocationDTO() {

    }

    public RouteLocationDTO(LocationDTO location, RouteStopDTO routeStop) {
        this.location = location;
        this.routeStop = routeStop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public RouteStopDTO getRouteStop() {
        return routeStop;
    }

    public void setRouteStop(RouteStopDTO routeStop) {
        this.routeStop = routeStop;
    }

}
