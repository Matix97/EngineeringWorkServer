package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.Data;

@Data
public class ChildDTO {
    private Long child_parent_id;
    private String child_name;
    private String child_password;
    private int child_radius_area;
    private double child_latitude;
    private double child_longitude;

    public Long getChild_parent_id() {
        return child_parent_id;
    }

    public void setChild_parent_id(Long child_parent_id) {
        this.child_parent_id = child_parent_id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getChild_password() {
        return child_password;
    }

    public void setChild_password(String child_password) {
        this.child_password = child_password;
    }

    public int getChild_radius_area() {
        return child_radius_area;
    }

    public void setChild_radius_area(int child_radius_area) {
        this.child_radius_area = child_radius_area;
    }

    public double getChild_latitude() {
        return child_latitude;
    }

    public void setChild_latitude(double child_latitude) {
        this.child_latitude = child_latitude;
    }

    public double getChild_longitude() {
        return child_longitude;
    }

    public void setChild_longitude(double child_longitude) {
        this.child_longitude = child_longitude;
    }
}
