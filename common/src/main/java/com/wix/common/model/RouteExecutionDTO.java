package com.wix.common.model;

import java.util.List;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutionDTO {

    private String id;

    private String routeRunId;

    private List<RouteExecutionLocationDTO> routeExecutionLocations;

    private long startTime; // utc time

    private long endTime; // utc time

    private RouteExecutorDTO routeExecutor;

    public RouteExecutionDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteRunId() {
        return routeRunId;
    }

    public void setRouteRunId(String routeRunId) {
        this.routeRunId = routeRunId;
    }

    public List<RouteExecutionLocationDTO> getRouteExecutionLocations() {
        return routeExecutionLocations;
    }

    public void setRouteExecutionLocations(List<RouteExecutionLocationDTO> routeExecutionLocations) {
        this.routeExecutionLocations = routeExecutionLocations;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public RouteExecutorDTO getRouteExecutor() {
        return routeExecutor;
    }

    public void setRouteExecutor(RouteExecutorDTO routeExecutor) {
        this.routeExecutor = routeExecutor;
    }

}
