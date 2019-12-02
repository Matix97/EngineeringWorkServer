package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.AdultRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdultService {
    @Autowired
    private AdultRepository adultRepository;

    public void saveAdult(AdultDTO adultDTO){
        Adult adult = new Adult();
        adult.setAdult_name(adultDTO.getAdult_name());
        adult.setAdult_surname(adultDTO.getAdult_surname());
        adult.setAdult_password(adultDTO.getAdult_password());
        adult.setAdult_phone_number(adultDTO.getAdult_phone_number());
        adult.setAdult_email_address(adultDTO.getAdult_email_address());
        adult.setAdult_suggested_toys_list("");
        adultRepository.save(adult);
    }
    public void deleteAdult(String adultId){ adultRepository.deleteById(Long.parseLong(adultId));}

    public List<Adult> getAddAdults(){
        List<Adult> result = new ArrayList<>();
        adultRepository.findAll().forEach(result::add);
        return result;
    }
}
