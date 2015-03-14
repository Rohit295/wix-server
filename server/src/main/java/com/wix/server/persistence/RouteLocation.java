package com.wix.server.persistence;

import com.wix.common.model.RouteLocationDTO;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;

    protected Location location;

    protected RouteStop routeStop;

    public RouteLocation() {
        setId(UUID.randomUUID().toString());
    }

    public RouteLocation(RouteLocationDTO dto) {

        this();

        if (dto.getLocation() != null) {
            setLocation(new Location(dto.getLocation()));
        }

        if (dto.getRouteStop() != null) {
            setRouteStop(new RouteStop(dto.getRouteStop()));
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RouteStop getRouteStop() {
        return routeStop;
    }

    public void setRouteStop(RouteStop routeStop) {
        this.routeStop = routeStop;
    }

    public RouteLocationDTO getDTO() {

        RouteLocationDTO dto = new RouteLocationDTO();

        dto.setId(id);

        if (location != null) {
            dto.setLocation(location.getDTO());
        }

        if (routeStop != null) {
            dto.setRouteStop(routeStop.getDTO());
        }

        return dto;

    }
}
