package com.wix.common.model;

import java.util.List;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteDTO {

    private String id;

    private String organizationId;

    private String name;

    private String defaultStopPurpose; // should be enum { Delivery, Pickup, Visit }

    private String executionStartTime; // a one time route or a scheduled route

    private List<RouteLocationDTO> routeLocations;

    public RouteDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultStopPurpose() {
        return defaultStopPurpose;
    }

    public void setDefaultStopPurpose(String defaultStopPurpose) {
        this.defaultStopPurpose = defaultStopPurpose;
    }

    public String getExecutionStartTime() {
        return executionStartTime;
    }

    public void setExecutionStartTime(String executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public List<RouteLocationDTO> getRouteLocations() {
        return routeLocations;
    }

    public void setRouteLocations(List<RouteLocationDTO> routeLocations) {
        this.routeLocations = routeLocations;
    }

}
