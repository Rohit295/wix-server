package com.wix.common.model;

/**
 * Created by racastur on 12-11-2014.
 */
public class RouteStopDTO {

    private String name;

    private String address; // This should be a class ideally

    public RouteStopDTO() {

    }

    public RouteStopDTO(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
