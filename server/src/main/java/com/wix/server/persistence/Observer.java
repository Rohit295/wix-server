package com.wix.server.persistence;

import java.util.List;
import java.util.UUID;

/**
 * Created by racastur on 12-11-2014.
 */
public class Observer {

    private String id;

    private String name;

    private String contactInfo;      // ideally an email id or sms # or some communication identifier

    private List<String> observableId; // one parent may be interested in multiple children

    public Observer() {
        setId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<String> getObservableId() {
        return observableId;
    }

    public void setObservableId(List<String> observableId) {
        this.observableId = observableId;
    }

}
