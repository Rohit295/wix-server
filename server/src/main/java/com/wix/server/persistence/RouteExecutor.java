package com.wix.server.persistence;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutor implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String userId;

	private String deviceId;

	public RouteExecutor() {
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