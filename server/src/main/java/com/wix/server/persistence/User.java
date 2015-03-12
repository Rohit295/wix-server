package com.wix.server.persistence;

import com.wix.common.model.UserInfo;

import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by racastur on 31-10-2014.
 */
@PersistenceCapable
public class User {

    @PrimaryKey
    @Persistent
    private String id;

    @Persistent
    private String organizationId;

    @Persistent
    private String emailId;

    public User() {
        setId(UUID.randomUUID().toString());
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

    public UserInfo getInfo() {

        UserInfo info = new UserInfo();
        info.setId(id);
        info.setOrganizationId(organizationId);
        info.setEmailId(emailId);

        return info;

    }

}
