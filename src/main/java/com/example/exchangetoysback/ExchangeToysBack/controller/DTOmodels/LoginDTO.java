package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.Data;

@Data
public class LoginDTO {
    private String identity;
    private String password;
    private String role;//two possibility: "adult" or "child"
}
