package com.wix.server.persistence;

import com.wix.common.model.*;

import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by racastur on 12-11-2014.
 */
@PersistenceCapable
public class RouteRun {

    @PrimaryKey
    @Persistent
    private String id;

    @Persistent
    private String routeId;

    @Persistent
    private String defaultStopPurpose = StopPurpose.Dropoff.name();

    @Persistent(serialized = "true")
    private RouteExecutor routeExecutor;

    @Persistent
    private String executionStartTime; // a one time route or a scheduled route

    public RouteRun() {
        setId(UUID.randomUUID().toString());
    }

    public RouteRun(RouteRunDTO dto) {

        this();

        setRouteId(dto.getRouteId());
        setDefaultStopPurpose(dto.getDefaultStopPurpose().name());
        setRouteExecutor(new RouteExecutor(dto.getRouteExecutor()));
        setExecutionStartTime(dto.getExecutionStartTime());

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

    public String getDefaultStopPurpose() {
        return defaultStopPurpose;
    }

    public void setDefaultStopPurpose(String defaultStopPurpose) {
        this.defaultStopPurpose = defaultStopPurpose;
    }

    public RouteExecutor getRouteExecutor() {
        return routeExecutor;
    }

    public void setRouteExecutor(RouteExecutor routeExecutor) {
        this.routeExecutor = routeExecutor;
    }

    public String getExecutionStartTime() {
        return executionStartTime;
    }

    public void setExecutionStartTime(String executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public RouteRunDTO getDTO() {

        RouteRunDTO dto = new RouteRunDTO();

        dto.setId(id);
        dto.setRouteId(routeId);
        dto.setDefaultStopPurpose(Enum.valueOf(StopPurpose.class, defaultStopPurpose));
        dto.setRouteExecutor(routeExecutor.getDTO());
        dto.setExecutionStartTime(executionStartTime);

        return dto;

    }
}
