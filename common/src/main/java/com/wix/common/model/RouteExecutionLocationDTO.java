package com.wix.common.model;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteExecutionLocationDTO {

	private String id;

	private LocationDTO location;

	private long timestamp; // utc time

	private RouteExecutionStopDTO routeExecutionStop;

	public RouteExecutionLocationDTO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public RouteExecutionStopDTO getRouteExecutionStop() {
		return routeExecutionStop;
	}

	public void setRouteExecutionStop(RouteExecutionStopDTO routeExecutionStop) {
		this.routeExecutionStop = routeExecutionStop;
	}

}
