package com.example.exchangetoysback.ExchangeToysBack.service.model;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "adult")
public class Adult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adult_id;
    @NotNull
    @Length(max = 50)
    private String adult_name;
    @NotNull
    @Length(max = 50)
    private String adult_surname;
    @NotNull
    @Length(max = 50)
    private String adult_password;
    @Length(max = 12)
    private String adult_phone_number;
    @NotNull
    @Length(max = 50)
    private String adult_email_address;
    @Length(max = 500)
    private String adult_suggested_toys_list;


}
