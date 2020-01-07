package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ChildUpdateDTO {

    private String child_login;
    private int child_radius_area;
    private ArrayList<String> availableAge;
    private String availableTag;


}
