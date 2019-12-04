package com.example.exchangetoysback.ExchangeToysBack.service.model;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "toy")
public class Toy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toy_id;
    @NotNull
    private Long toy_owner_id;//foreign key to adult
    private Long toy_current_holder_id;
    @NotNull
    @Length(max = 50)
    private String toy_name;
    @Length(max = 1000)
    private String toy_description;
    private int toy_age_category;
    @NotNull
    @Length(max = 10)
    private String toy_main_category;
    @Length(max = 200)
    private String toy_special_feature;
    @Length(max = 200)
    private String toy_tags;
    private int toy_didactic;
    private int toy_vintage;
    @Length(max = 50)
    private String toy_factory_name;
    private int toy_quality_of_made;
    @Length(max = 1500)
    private String toy_photos;

    public Long getToy_id() {
        return toy_id;
    }

    public void setToy_id(Long toy_id) {
        this.toy_id = toy_id;
    }

    public Long getToy_owner_id() {
        return toy_owner_id;
    }

    public void setToy_owner_id(Long toy_owner_id) {
        this.toy_owner_id = toy_owner_id;
    }

    public Long getToy_current_holder_id() {
        return toy_current_holder_id;
    }

    public void setToy_current_holder_id(Long toy_current_holder_id) {
        this.toy_current_holder_id = toy_current_holder_id;
    }

    public String getToy_name() {
        return toy_name;
    }

    public void setToy_name(String toy_name) {
        this.toy_name = toy_name;
    }

    public String getToy_description() {
        return toy_description;
    }

    public void setToy_description(String toy_description) {
        this.toy_description = toy_description;
    }

    public int getToy_age_category() {
        return toy_age_category;
    }

    public void setToy_age_category(int toy_age_category) {
        this.toy_age_category = toy_age_category;
    }

    public String getToy_main_category() {
        return toy_main_category;
    }

    public void setToy_main_category(String toy_main_category) {
        this.toy_main_category = toy_main_category;
    }

    public String getToy_special_feature() {
        return toy_special_feature;
    }

    public void setToy_special_feature(String toy_special_feature) {
        this.toy_special_feature = toy_special_feature;
    }

    public String getToy_tags() {
        return toy_tags;
    }

    public void setToy_tags(String toy_tags) {
        this.toy_tags = toy_tags;
    }

    public int getToy_didactic() {
        return toy_didactic;
    }

    public void setToy_didactic(int toy_didactic) {
        this.toy_didactic = toy_didactic;
    }

    public int getToy_vintage() {
        return toy_vintage;
    }

    public void setToy_vintage(int toy_vintage) {
        this.toy_vintage = toy_vintage;
    }

    public String getToy_factory_name() {
        return toy_factory_name;
    }

    public void setToy_factory_name(String toy_factory_name) {
        this.toy_factory_name = toy_factory_name;
    }

    public int getToy_quality_of_made() {
        return toy_quality_of_made;
    }

    public void setToy_quality_of_made(int toy_quality_of_made) {
        this.toy_quality_of_made = toy_quality_of_made;
    }

    public String getToy_photos() {
        return toy_photos;
    }

    public void setToy_photos(String toy_photos) {
        this.toy_photos = toy_photos;
    }
}
