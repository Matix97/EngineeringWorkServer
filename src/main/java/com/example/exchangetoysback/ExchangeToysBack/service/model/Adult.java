package com.example.exchangetoysback.ExchangeToysBack.service.model;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @Length(max = 500)
    private String adult_password;
    @Length(max = 12)
    private String adult_phone_number;
    @NotNull
    @Length(max = 50)
    private String adult_email_address;
    @Length(max = 500)
    private String adult_suggested_toys_list;
//    @OneToMany( targetEntity=Adult.class )
//    private List employee_list;


    public Long getAdult_id() {
        return adult_id;
    }

    public void setAdult_id(Long adult_id) {
        this.adult_id = adult_id;
    }

    public String getAdult_name() {
        return adult_name;
    }

    public void setAdult_name(String adult_name) {
        this.adult_name = adult_name;
    }

    public String getAdult_surname() {
        return adult_surname;
    }

    public void setAdult_surname(String adult_surname) {
        this.adult_surname = adult_surname;
    }

    public String getAdult_password() {
        return adult_password;
    }

    public void setAdult_password(String adult_password) {
        this.adult_password = adult_password;
    }

    public String getAdult_phone_number() {
        return adult_phone_number;
    }

    public void setAdult_phone_number(String adult_phone_number) {
        this.adult_phone_number = adult_phone_number;
    }

    public String getAdult_email_address() {
        return adult_email_address;
    }

    public void setAdult_email_address(String adult_email_address) {
        this.adult_email_address = adult_email_address;
    }

    public String getAdult_suggested_toys_list() {
        return adult_suggested_toys_list;
    }

    public void setAdult_suggested_toys_list(String adult_suggested_toys_list) {
        this.adult_suggested_toys_list = adult_suggested_toys_list;
    }

    @Override
    public String toString() {
        return "Adult{" +
                "adult_id=" + adult_id +
                ", adult_name='" + adult_name + '\'' +
                ", adult_surname='" + adult_surname + '\'' +
                ", adult_password='" + adult_password + '\'' +
                ", adult_phone_number='" + adult_phone_number + '\'' +
                ", adult_email_address='" + adult_email_address + '\'' +
                ", adult_suggested_toys_list='" + adult_suggested_toys_list + '\'' +
                '}';
    }
}
