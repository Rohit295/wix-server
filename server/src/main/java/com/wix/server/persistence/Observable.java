package com.wix.server.persistence;

import com.wix.common.model.ObservableDTO;
import com.wix.common.model.StopPurpose;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.UUID;

/**
 * Created by racastur on 12-11-2014.
 */
@PersistenceCapable
public class Observable {

    @PrimaryKey
    @Persistent
    private String id;

    @Persistent
    private String organizationId;

    @Persistent
    private String name;

    @Persistent
    private String routeId;

    @Persistent
    private String routeStopId;   // for school scenario, how can we model pick up from a stop but drop at a different stop??

    @Persistent
    private String stopPurpose = StopPurpose.Dropoff.name();

    public Observable() {
        setId(UUID.randomUUID().toString());
    }

    public Observable(ObservableDTO dto) {

        this();

        setOrganizationId(dto.getOrganizationId());
        setName(dto.getName());
        setRouteId(dto.getRouteId());
        setRouteStopId(dto.getRouteStopId());
        setStopPurpose(dto.getStopPurpose().name());

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

    public String getStopPurpose() {
        return stopPurpose;
    }

    public void setStopPurpose(String stopPurpose) {
        this.stopPurpose = stopPurpose;
    }

    public ObservableDTO getDTO() {

        ObservableDTO dto = new ObservableDTO();
        dto.setId(id);
        dto.setOrganizationId(organizationId);
        dto.setName(name);
        dto.setRouteId(routeId);
        dto.setRouteStopId(routeStopId);
        dto.setStopPurpose(Enum.valueOf(StopPurpose.class, stopPurpose));

        return dto;

    }

}