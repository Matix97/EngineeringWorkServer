package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.RentalDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.RentalRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Rental;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final AdultService adultService;
    private final ToyService toyService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RentalService(RentalRepository rentalRepository, AdultService adultService, ToyService toyService) {
        this.rentalRepository = rentalRepository;
        this.adultService = adultService;
        this.toyService = toyService;

    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public Rental creat(RentalDTO rentalDTO, Long toyId) {
        Rental rental = new Rental();

        try {
            authenticate(rentalDTO.getFutureHolderEmail(), rentalDTO.getFutureHolderPassword());//check future holder's credentials
            if (toyService.ifExist(toyId)) {
                Toy t = toyService.getById(toyId);
                if (t.getToy_owner_id().equals(TokenInfo.getUserName())) {//check if is correct owner
                    //ID
                    rental.setToyIdToTransaction(toyId);
                    //FUTURE
                    rental.setFutureHolder(rentalDTO.getFutureHolderEmail());
                    //photos
                    StringBuilder photos = new StringBuilder();
                    for (String s : rentalDTO.getPhotos()) {
                        photos.append(s);
                        photos.append(";");
                    }
                    rental.setPhotos(photos.toString());
                    //DATA rent
                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    try {
                        rental.setRentDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //Data return
                    String[] d = rentalDTO.getSuggestedReturnDate().split("-");
                    int year = Integer.parseInt(d[0]), month = Integer.parseInt(d[1]), day = Integer.parseInt(d[2]);
                    try {
                        rental.setReturnDate(simpleDateFormat.parse(simpleDateFormat.format(getDate(year, month, day))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    rental.setIfReturned(0);
                    rental.setTypOfTransaction("timeRental");
                    rental.setCurrentOwner(TokenInfo.getUserName());
                    rentalRepository.save(rental);
                    return rental;
                } else
                    return null;

            } else
                return null;


        } catch (Exception e) {
            return null;
        }
//
//
//
//
//
//
//
//        try {
//        //    System.out.println("Try set return data: " + rentalDTO.getSuggestedReturnDate());
//          //  rental.setSuggestedReturnDate(rentalDTO.getSuggestedReturnDate());
//        } catch (Exception e) {
//
//        }
//
//        rental.setCurrentOwner(TokenInfo.getUserName());
////        if (adultService.ifExist(rentalDTO.getFutureHolder()))
////            rental.setFutureHolder(rentalDTO.getFutureHolder());
////        else
////            return null;
//
//        rental.setIfReturned(0);
//     //   rental.setTypOfTransaction(rentalDTO.getTypOfTransaction());
//        rental.setTypOfTransaction("timeRental");
//        //rental.setMoney(rentalDTO.getMoney());
//       // rentalRepository.save(rental);


    }
}
