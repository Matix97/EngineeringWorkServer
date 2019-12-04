package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.Data;

@Data
public class AdultDTO {
    private String adult_name;
    private String adult_surname;
    private String adult_password;
    private String adult_phone_number;
    private String adult_email_address;

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
}
