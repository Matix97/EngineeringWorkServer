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
    private Long child_parent_id;
    @NotNull
    @Length(max = 50)
    private String child_name;
    @NotNull
    @Length(max = 50)
    private String child_password;
    private int child_radius_area;
    private double child_latitude;
    private double child_longitude;

}
