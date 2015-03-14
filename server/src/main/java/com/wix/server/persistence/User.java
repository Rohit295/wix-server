package com.wix.server.persistence;

import com.wix.common.model.UserDTO;

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
    private String name;

    @Persistent
    private String userName;

    @Persistent
    private String emailId;

    @Persistent
    private boolean adminRole;

    public User() {
        setId(UUID.randomUUID().toString());
    }

    public User(UserDTO dto) {

        this();

        setOrganizationId(dto.getOrganizationId());
        setName(dto.getName());
        setUserName(dto.getUserName());
        setEmailId(dto.getEmailId());
        setAdminRole(dto.isAdminRole());

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean isAdminRole() {
        return adminRole;
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }

    public UserDTO getDTO() {

        UserDTO info = new UserDTO();
        info.setId(id);
        info.setOrganizationId(organizationId);
        info.setName(name);
        info.setUserName(userName);
        info.setEmailId(emailId);
        info.setAdminRole(adminRole);

        return info;

    }

}
