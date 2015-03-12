package com.wix.common.model;

/**
 * Created by racastur on 01-11-2014.
 */
public class UserInfo {

    private String id;

    private String organizationId;

    private String emailId;

    public UserInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
