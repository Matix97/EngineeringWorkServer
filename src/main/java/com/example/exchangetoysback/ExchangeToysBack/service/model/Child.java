package com.example.exchangetoysback.ExchangeToysBack.service.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "child")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long child_id;
    @NotNull
    private String child_parent_id;//generally it's parent's email
    @NotNull
    @Length(max = 50)
    private String child_name;
    @NotNull
    @Length(max = 50)
    private String child_login;
    @NotNull
    @Length(max = 500)
    private String child_password;
    @NotNull
    private int child_age;
    private int child_radius_area;
    private double child_latitude;
    private double child_longitude;
    private String child_suggestion;
    private String availableAge;
    private String availableTag;


}
