package com.wix.common.model;

import java.util.List;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutionDTO {

	private String id;

	private String routeId;

	private List<RouteExecutionLocationDTO> routeExecutionLocations;

	private long startTime; // utc time

	private long endTime; // utc time

	private String routeExecutorId;

	public RouteExecutionDTO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public List<RouteExecutionLocationDTO> getRouteExecutionLocations() {
		return routeExecutionLocations;
	}

	public void setRouteExecutionLocations(List<RouteExecutionLocationDTO> routeExecutionLocations) {
		this.routeExecutionLocations = routeExecutionLocations;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getRouteExecutorId() {
		return routeExecutorId;
	}

	public void setRouteExecutorId(String routeExecutorId) {
		this.routeExecutorId = routeExecutorId;
	}

}
