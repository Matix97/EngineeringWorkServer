package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.RentalDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.RentalRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Rental;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final AdultService adultService;
    private final ToyService toyService;

    public RentalService(RentalRepository rentalRepository, AdultService adultService, ToyService toyService) {
        this.rentalRepository = rentalRepository;
        this.adultService = adultService;
        this.toyService = toyService;
    }

    public Rental creat(RentalDTO rentalDTO, Long toyId) {
        Rental rental = new Rental();
        //photos
        StringBuilder photos = new StringBuilder();
        for (String s : rentalDTO.getPhotos()) {
            photos.append(s);
            photos.append(";");
        }
        rental.setPhotos(photos.toString());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            rental.setRentDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Try set return data: " + rentalDTO.getSuggestedReturnDate());
            rental.setSuggestedReturnDate(rentalDTO.getSuggestedReturnDate());
        } catch (Exception e) {

        }

        rental.setCurrentOwner(TokenInfo.getUserName());
        if (adultService.ifExist(rentalDTO.getFutureHolder()))
            rental.setFutureHolder(rentalDTO.getFutureHolder());
        else
            return null;
        if (toyService.ifExist(toyId))
            rental.setToyIdToTransaction(toyId);
        else
            return null;
        rental.setIfReturned(0);
        rental.setTypOfTransaction(rentalDTO.getTypOfTransaction());
        rental.setMoney(rentalDTO.getMoney());
        rentalRepository.save(rental);

        return rental;
    }
}
