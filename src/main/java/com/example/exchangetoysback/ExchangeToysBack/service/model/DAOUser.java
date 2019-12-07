package com.example.exchangetoysback.ExchangeToysBack.service.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class DAOUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //@Column(name = "username")
    private String username;
    //@Column(name = "pass")
    private String pass;


}
