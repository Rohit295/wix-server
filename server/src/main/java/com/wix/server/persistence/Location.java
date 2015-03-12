package com.wix.server.persistence;

import com.wix.common.model.*;

import java.io.Serializable;

/**
 * Created by racastur on 12-11-2014.
 */
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double latitude;

	private Double longitude;

	private String address;

	public Location() {

	}

	public Location(LocationDTO dto) {
		setLatitude(dto.getLatitude());
		setLongitude(dto.getLongitude());
		setAddress(dto.getAddress());
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocationDTO getDTO() {

		LocationDTO dto = new LocationDTO();

		dto.setLatitude(latitude);
		dto.setLongitude(longitude);
		dto.setAddress(address);

		return dto;

	}
}
