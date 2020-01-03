package com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
public class RentalDTO {
    private Date rentDate;
    private Date suggestedReturnDate;
    // private Date returnDate;
    private ArrayList<String> photos;
    //  private String currentOwner; in token
    private String futureHolder;
    private Long toyIdToTransaction;
    private Long secondToyIdToTransaction;//must when exchange
    //   private Integer ifReturned;//1 if yes, 0 if no
    private String typOfTransaction;//exchange(wymiana), commitment(oddanie), rental(wypożyczenie), {timeExChange, endlessExchange, moneyCommitment, freeCommitment,moneyTimeRental, freeTimeRental}
    private Double money;

}