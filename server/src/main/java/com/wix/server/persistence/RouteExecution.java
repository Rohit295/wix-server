package com.wix.server.persistence;

import com.wix.common.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by racastur on 12-11-2014.
 */
@PersistenceCapable
public class RouteExecution {

    @PrimaryKey
    @Persistent
    private String id;

    @Persistent
    private String routeId;

    @Persistent(serialized = "true")
    private List<RouteExecutionLocation> routeExecutionLocations;

    @Persistent
    private long startTime;  // utc time

    @Persistent
    private long endTime;    // utc time

    @Persistent
    private String routeExecutorId;

    public RouteExecution() {
        setId(UUID.randomUUID().toString());
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

    public List<RouteExecutionLocation> getRouteExecutionLocations() {
        return routeExecutionLocations;
    }

    public void setRouteExecutionLocations(List<RouteExecutionLocation> routeExecutionLocations) {
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

    public String getRouteExecutorId() {
        return routeExecutorId;
    }

    public void setRouteExecutorId(String routeExecutorId) {
        this.routeExecutorId = routeExecutorId;
    }

    public RouteExecutionDTO getDTO() {

        RouteExecutionDTO dto = new RouteExecutionDTO();
        dto.setId(id);
        dto.setRouteId(routeId);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);

        List<RouteExecutionLocationDTO> locationDTOs = new ArrayList<RouteExecutionLocationDTO>();
        if (routeExecutionLocations != null) {
            for (RouteExecutionLocation location : routeExecutionLocations) {
                locationDTOs.add(location.getDTO());
            }
        }
        dto.setRouteExecutionLocations(locationDTOs);

        return dto;

    }

}
