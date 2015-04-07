package com.wix.common.model;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutorDTO {

    private String id;

    private String userId;

    private String deviceId;

    public RouteExecutorDTO() {
    }

    public RouteExecutorDTO(String userId, String deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}