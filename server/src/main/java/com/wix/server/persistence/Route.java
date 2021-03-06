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
public class Route {

    @PrimaryKey
    @Persistent
    private String id;

    @Persistent
    private String name;

    @Persistent
    private String organizationId;

    @Persistent(serialized = "true")
    private List<RouteLocation> routeLocations;

    public Route() {
        setId(UUID.randomUUID().toString());
    }

    public Route(RouteDTO dto) {

        this();

        setName(dto.getName());
        setOrganizationId(dto.getOrganizationId());

        List<RouteLocation> routeLocations = new ArrayList<RouteLocation>();
        if (dto.getRouteLocations() != null) {
            for (RouteLocationDTO routeLocationDTO : dto.getRouteLocations()) {
                routeLocations.add(new RouteLocation(routeLocationDTO));
            }
        }
        setRouteLocations(routeLocations);

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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public List<RouteLocation> getRouteLocations() {
        return routeLocations;
    }

    public void setRouteLocations(List<RouteLocation> routeLocations) {
        this.routeLocations = routeLocations;
    }

    public RouteDTO getDTO() {

        RouteDTO dto = new RouteDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setOrganizationId(organizationId);

        List<RouteLocationDTO> routeLocationDTOs = new ArrayList<RouteLocationDTO>();
        if (routeLocations != null) {
            for (RouteLocation routeLocation : routeLocations) {
                routeLocationDTOs.add(routeLocation.getDTO());
            }
        }
        dto.setRouteLocations(routeLocationDTOs);

        return dto;

    }

}
