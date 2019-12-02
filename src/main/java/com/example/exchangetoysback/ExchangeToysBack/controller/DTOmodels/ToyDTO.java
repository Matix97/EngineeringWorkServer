package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.Data;

@Data
public class ToyDTO {
    private Long toy_owner_id;//foreign key to adult
    private Long toy_current_holder_id;
    private String toy_name;
    private String toy_description;
    private int toy_age_category;
    private String toy_main_category;
    private String toy_special_feature;
    private String toy_tags;
    private int toy_didactic;
    private int toy_vintage;
    private String toy_factory_name;
    private int toy_quality_of_made;
    private String toy_photos;
}
