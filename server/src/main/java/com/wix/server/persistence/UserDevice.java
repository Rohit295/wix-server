package com.wix.server.persistence;

import java.util.UUID;

/**
 * Created by racastur on 31-10-2014.
 */
public class UserDevice {

    private String id;

    private String userId;

    private String deviceId;

    public UserDevice() {
        setId(UUID.randomUUID().toString());
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
