package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AddToyDTO {
    private String name;
    private String description;
    private int ageRange;//mapowanie 0-3= 1,4-7=2,8-12=3,13-15=4, 16-100=5
    private String category;
    private ArrayList<String> tags;//list of tags witch could categorize toys
    private boolean ifDidactic;//true if yes
    private boolean ifVintage;//true if yes
    private String toysFactoryName;
    private int qualityOfMade;//1-10
    private ArrayList<String> photosURLs;
    private double toy_latitude;
    private double toy_longitude;
    private Double money;
    private String typOfTransaction;

}
