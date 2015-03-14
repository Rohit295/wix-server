package com.wix.server.persistence;

import com.wix.common.model.RouteExecutionLocationDTO;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutionLocation {

    private String id;

    private String routeExecutionId;

    private Location location;

    private long timestamp; // utc time

    private RouteExecutionStop routeExecutionStop;

    public RouteExecutionLocation() {
        setId(UUID.randomUUID().toString());
    }

    public RouteExecutionLocation(String routeExecutionId, RouteExecutionLocationDTO dto) {

        this();

        setRouteExecutionId(routeExecutionId);
        setLocation(new Location(dto.getLocation()));
        setTimestamp(Calendar.getInstance().getTimeInMillis());

        if (dto.getRouteExecutionStop() != null) {
            setRouteExecutionStop(new RouteExecutionStop(dto.getRouteExecutionStop()));
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteExecutionId() {
        return routeExecutionId;
    }

    public void setRouteExecutionId(String routeExecutionId) {
        this.routeExecutionId = routeExecutionId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public RouteExecutionStop getRouteExecutionStop() {
        return routeExecutionStop;
    }

    public void setRouteExecutionStop(RouteExecutionStop routeExecutionStop) {
        this.routeExecutionStop = routeExecutionStop;
    }

    public RouteExecutionLocationDTO getDTO() {

        RouteExecutionLocationDTO dto = new RouteExecutionLocationDTO();
        dto.setId(id);
        dto.setLocation(location.getDTO());
        dto.setTimestamp(timestamp);

        if (routeExecutionStop != null) {
            dto.setRouteExecutionStop(routeExecutionStop.getDTO());
        }

        return dto;

    }

}
