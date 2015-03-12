package com.wix.common.model;

/**
 * Created by racastur on 02-03-2015.
 */
public class OrganizationDTO {

    private String id;

    private String name;

    private String orgType;

    public OrganizationDTO() {

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

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

}
