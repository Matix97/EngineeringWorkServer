package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterDTO {
    private String mainCategory;
    private String age;
    private String tags;
    private String anyKeyword;
    private boolean isDidactic;
    private boolean isVintage;
    private double latitude;
    private double longitude;
}
