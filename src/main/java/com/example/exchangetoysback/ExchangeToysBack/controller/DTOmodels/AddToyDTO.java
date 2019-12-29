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

    public void setAgeRange(String ageRange) {
        switch (ageRange) {
            case "0-3":
                this.ageRange = 1;
                break;
            case "4-7":
                this.ageRange = 2;
                break;
            case "8-12":
                this.ageRange = 3;
                break;
            case "13-15":
                this.ageRange = 4;
                break;
            case "16-100":
                this.ageRange = 5;
                break;
        }
    }
}
