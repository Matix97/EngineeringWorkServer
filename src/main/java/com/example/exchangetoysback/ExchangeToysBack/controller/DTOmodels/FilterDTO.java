package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterDTO {
    private String mainCategory;//null if any category
    private String age;//null if each age
    private String tags;//null if any category
    private String anyKeyword;//null if doesn't matter
    private Boolean isDidactic;//null if both
    private Boolean isVintage;//null if both
    private double latitude;
    private double longitude;
    private Integer radius;

    public Integer getAge() {
        if (age == null)
            return null;
        switch (age) {
            case "0-3":
                return 1;
            case "4-7":
                return 2;
            case "8-12":
                return 3;
            case "13-15":
                return 4;
            case "16-100":
                return 5;
        }
        return null;
    }

    public boolean isPseudoEmpty() {
        return mainCategory == null && age == null && tags == null && radius == null
                && anyKeyword == null && isDidactic == null && isVintage == null;
    }

    public Integer isDidacticNumber() {
        if (isDidactic == null) return null;
        return isDidactic ? 1 : 0;
    }

    public Integer isVintageNumber() {
        if (isVintage == null) return null;
        return isVintage ? 1 : 0;
    }
}
