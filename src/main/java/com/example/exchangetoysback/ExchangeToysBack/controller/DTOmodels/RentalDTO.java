package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
    //private Date rentDate;
    private String suggestedReturnDate;//yyyy-MM-dd
    private ArrayList<String> photos;
    private String futureHolderEmail;
    private String futureHolderPassword;
    private Long toyIdTo;

}
