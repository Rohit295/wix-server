package com.wix.server.persistence;

import com.wix.common.model.OrganizationDTO;

import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by racastur on 02-03-2015.
 */
@PersistenceCapable
public class Organization {

    @PrimaryKey
    @Persistent
    private String id;

    @Persistent
    private String name;

    @Persistent
    private String orgType;

    public Organization() {
        setId(UUID.randomUUID().toString());
    }

    public Organization(OrganizationDTO dto) {

        this();

        setName(dto.getName());
        setOrgType(dto.getOrgType());

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

    public OrganizationDTO getDTO() {

        OrganizationDTO dto = new OrganizationDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setOrgType(orgType);

        return dto;

    }
}
