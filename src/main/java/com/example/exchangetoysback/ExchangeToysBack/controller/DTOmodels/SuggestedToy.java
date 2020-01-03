package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;


import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuggestedToy {
    Child child;
    Toy toy;
}
