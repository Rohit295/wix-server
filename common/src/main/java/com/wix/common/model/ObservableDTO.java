package com.wix.common.model;

/**
 * Created by racastur on 12-11-2014.
 */
public class ObservableDTO {

    private String id;

    private String organizationId;

    private String name;

    private String routeId;

    private String routeStopId;   // for school scenario, how can we model pick up from a stop but drop at a different stop??

    private StopPurpose stopPurpose; // inherits defaultStopPurpose from Route, but can be overridden

    public ObservableDTO() {

    }

    public ObservableDTO(String organizationId, String name, String routeId, String routeStopId, StopPurpose stopPurpose) {
        this.organizationId = organizationId;
        this.name = name;
        this.routeId = routeId;
        this.routeStopId = routeStopId;
        this.stopPurpose = stopPurpose;
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

    public StopPurpose getStopPurpose() {
        return stopPurpose;
    }

    public void setStopPurpose(StopPurpose stopPurpose) {
        this.stopPurpose = stopPurpose;
    }

}