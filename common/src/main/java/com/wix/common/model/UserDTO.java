package com.wix.common.model;

/**
 * Created by racastur on 01-11-2014.
 */
public class UserDTO {

    private String id;

    private String organizationId;

    private String name;

    private String userName;

    private String password;

    private String emailId;

    private boolean adminRole;

    public UserDTO() {

    }

    public UserDTO(String organizationId, String name, String userName, String password, String emailId, boolean adminRole) {
        this.organizationId = organizationId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.emailId = emailId;
        this.adminRole = adminRole;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
