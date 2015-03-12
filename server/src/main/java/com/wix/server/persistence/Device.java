package com.wix.server.persistence;

import java.util.UUID;

/**
 * Created by racastur on 31-10-2014.
 */
public class Device {

	private String id;

	private String gcmRegistrationId;

	public Device() {
		setId(UUID.randomUUID().toString());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGcmRegistrationId() {
		return gcmRegistrationId;
	}

	public void setGcmRegistrationId(String gcmRegistrationId) {
		this.gcmRegistrationId = gcmRegistrationId;
	}

}
