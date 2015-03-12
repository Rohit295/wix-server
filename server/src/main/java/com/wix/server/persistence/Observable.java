package com.wix.server.persistence;

import java.util.UUID;

/**
 * Created by racastur on 12-11-2014.
 */
public class Observable {

    private String id;

    private String name;

    private String routeId;

    private String routeStopId;   // for school scenario, how can we model pick up from a stop but drop at a different stop??

    private String stopPurpose; // inherits defaultStopPurpose from Route, but can be overridden

    public Observable() {
        setId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(String routeStopId) {
        this.routeStopId = routeStopId;
    }

    public String getStopPurpose() {
        return stopPurpose;
    }

    public void setStopPurpose(String stopPurpose) {
        this.stopPurpose = stopPurpose;
    }

}