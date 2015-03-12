package com.wix.server.persistence;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by racastur on 12-11-2014.
 */
public class ObservationStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String observableId;

	private String routeExecutionStopId;

	private String status; // could be a boolean, but ideally an enum??

	private String notes; // free form field for the executor to put down notes/comments

	public ObservationStatus() {
		setId(UUID.randomUUID().toString());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObservableId() {
		return observableId;
	}

	public void setObservableId(String observableId) {
		this.observableId = observableId;
	}

	public String getRouteExecutionStopId() {
		return routeExecutionStopId;
	}

	public void setRouteExecutionStopId(String routeExecutionStopId) {
		this.routeExecutionStopId = routeExecutionStopId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
