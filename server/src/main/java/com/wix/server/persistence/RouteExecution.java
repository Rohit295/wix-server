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
    private String routeRunId;

    @Persistent(serialized = "true")
    private List<RouteExecutionLocation> routeExecutionLocations;

    // List of Consumers that are currently interested in this RouteExecution
    // TODO - this is not really channel and instead a ConsumerToken that we are updating
    @Persistent(serialized = "true")
    private ArrayList<String> channelsToUpdate;

    @Persistent
    private long startTime;  // utc time

    @Persistent
    private long endTime;    // utc time

    @Persistent(serialized = "true")
    private RouteExecutor routeExecutor;

    public RouteExecution() {
        setId(UUID.randomUUID().toString());
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

    public RouteExecutor getRouteExecutor() {
        return routeExecutor;
    }

    public void setRouteExecutor(RouteExecutor routeExecutor) {
        this.routeExecutor = routeExecutor;
    }

    public RouteExecutionDTO getDTO() {

        RouteExecutionDTO dto = new RouteExecutionDTO();
        dto.setId(id);
        dto.setRouteRunId(routeRunId);

        List<RouteExecutionLocationDTO> locationDTOs = new ArrayList<RouteExecutionLocationDTO>();
        if (routeExecutionLocations != null) {
            for (RouteExecutionLocation location : routeExecutionLocations) {
                locationDTOs.add(location.getDTO());
            }
        }
        dto.setRouteExecutionLocations(locationDTOs);

        dto.setStartTime(startTime);
        dto.setEndTime(endTime);

        if (routeExecutor != null) {
            dto.setRouteExecutor(routeExecutor.getDTO());
        }

        return dto;
    }

    public ArrayList<String> getChannelsToUpdate() {
        return channelsToUpdate;
    }

    public void setChannelsToUpdate(ArrayList<String> channelsToUpdate) {
        this.channelsToUpdate = channelsToUpdate;
    }

    public void addChannelToUpdate(String channelToUpdate) {
        this.channelsToUpdate.add(channelToUpdate);
    }

    public void removeChannelFromUpdate(String channelToRemove) {
        channelsToUpdate.remove(channelToRemove);
    }

}
