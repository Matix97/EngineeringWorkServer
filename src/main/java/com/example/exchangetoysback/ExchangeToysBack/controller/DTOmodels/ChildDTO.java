package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.Data;

@Data
public class ChildDTO {
    private String child_parent_id;//parent's email
    private String child_name;
    private String child_login;
    private String child_password;
    private int child_radius_area;
    private double child_latitude;
    private double child_longitude;
    private int child_age;
    private String availableAge;
    private String availableTag;
    private Integer amountOfSuggesstedToy;


}
