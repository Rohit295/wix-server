package com.wix.server.persistence;

import com.wix.common.model.TrackLocationInfo;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by racastur on 31-10-2014.
 */
@Entity
public class TrackLocation {

    @Id
    private Long id;

    @Index
    private Long trackId;

    private Long deviceId;

    private Long timestamp;

    private Double latitude;

    private Double longitude;

    public TrackLocation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    public TrackLocationInfo getInfo() {

        TrackLocationInfo info = new TrackLocationInfo();
        info.setId(getId());
        info.setDeviceId(getDeviceId());
        info.setTimestamp(getTimestamp());
        info.setLatitude(getLatitude());
        info.setLongitude(getLongitude());

        return info;

    }


}
