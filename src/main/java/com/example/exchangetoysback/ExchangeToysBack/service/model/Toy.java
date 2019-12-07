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


}
