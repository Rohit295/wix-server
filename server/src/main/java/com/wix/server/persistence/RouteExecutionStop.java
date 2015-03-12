package com.wix.server.persistence;

import com.wix.common.model.RouteExecutionStopDTO;

import java.io.Serializable;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutionStop implements Serializable {

	private static final long serialVersionUID = 1L;

	private String routeStopId;

	public RouteExecutionStop() {
	}

	public RouteExecutionStop(RouteExecutionStopDTO dto) {

		this();

		setRouteStopId(dto.getRouteStopId());

	}

	public String getRouteStopId() {
		return routeStopId;
	}

	public void setRouteStopId(String routeStopId) {
		this.routeStopId = routeStopId;
	}

	public RouteExecutionStopDTO getDTO() {

		RouteExecutionStopDTO dto = new RouteExecutionStopDTO();
		dto.setRouteStopId(routeStopId);

		return dto;

	}

}
