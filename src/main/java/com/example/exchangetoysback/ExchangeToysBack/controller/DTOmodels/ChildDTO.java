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
}
