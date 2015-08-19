package com.wix.common.model;

import java.util.List;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteRunDTO {

    private String id;

    private String routeId;

    private StopPurpose defaultStopPurpose;

    private RouteExecutorDTO routeExecutor;

    private String executionStartTime; // a one time route or a scheduled route

    private List<String> observableIds;

    public RouteRunDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public StopPurpose getDefaultStopPurpose() {
        return defaultStopPurpose;
    }

    public void setDefaultStopPurpose(StopPurpose defaultStopPurpose) {
        this.defaultStopPurpose = defaultStopPurpose;
    }

    public RouteExecutorDTO getRouteExecutor() {
        return routeExecutor;
    }

    public void setRouteExecutor(RouteExecutorDTO routeExecutor) {
        this.routeExecutor = routeExecutor;
    }

    public String getExecutionStartTime() {
        return executionStartTime;
    }

    public void setExecutionStartTime(String executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public List<String> getObservableIds() {
        return observableIds;
    }

    public void setObservableIds(List<String> observableIds) {
        this.observableIds = observableIds;
    }

}