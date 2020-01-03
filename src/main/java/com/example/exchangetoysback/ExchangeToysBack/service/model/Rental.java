package com.example.exchangetoysback.ExchangeToysBack.service.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rental_id;
    @NotNull
    private Date rentDate;
    private Date suggestedReturnDate;
    private Date returnDate;
    @Length(max = 500)
    private String photos;
    @NotNull
    @Length(max = 50)
    private String currentOwner;
    @NotNull
    @Length(max = 50)
    private String futureHolder;
    @NotNull
    private Long toyIdToTransaction;
    private Long secondToyIdToTransaction;//must when exchange
    @NotNull
    private Integer ifReturned;//1 if yes, 0 if no
    @NotNull
    @Length(max = 20)
    private String typOfTransaction;//exchange(wymiana), commitment(oddanie), rental(wypożyczenie), {timeExChange, endlessExchange, moneyCommitment, freeCommitment, timeRental}
    private Double money;

}
