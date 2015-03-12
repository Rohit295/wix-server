package com.wix.server.persistence;

import com.wix.common.model.RouteStopDTO;

import java.io.Serializable;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteStop implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String address; // This should be a class ideally

	public RouteStop() {
	}

	public RouteStop(RouteStopDTO dto) {
		setName(dto.getName());
		setAddress(dto.getAddress());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public RouteStopDTO getDTO() {

		RouteStopDTO dto = new RouteStopDTO();

		dto.setName(name);
		dto.setAddress(address);

		return dto;

	}
}
