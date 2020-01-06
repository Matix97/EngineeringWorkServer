package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.SuggestedToy;
import com.example.exchangetoysback.ExchangeToysBack.repository.AdultRepository;
import com.example.exchangetoysback.ExchangeToysBack.repository.ChildRepository;
import com.example.exchangetoysback.ExchangeToysBack.repository.ToyRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdultService implements UserDetailsService {
    private final AdultRepository adultRepository;

    private final ChildRepository childRepository;
    private final ToyRepository toyRepository;

    private final PasswordEncoder bcryptEncoder;

    public AdultService(AdultRepository adultRepository, ChildRepository childRepository, ToyRepository toyRepository, PasswordEncoder bcryptEncoder) {
        this.adultRepository = adultRepository;
        this.childRepository = childRepository;
        this.toyRepository = toyRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    public boolean ifExist(String email) {
        Adult adult = adultRepository.findByEmail(email);
        return adult != null;
    }

    public void saveAdult(AdultDTO adultDTO) {
        Adult adult = new Adult();
        adult.setAdult_name(adultDTO.getAdult_name());
        adult.setAdult_surname(adultDTO.getAdult_surname());
        adult.setAdult_password(bcryptEncoder.encode(adultDTO.getAdult_password()));
        adult.setAdult_phone_number(adultDTO.getAdult_phone_number());
        adult.setAdult_email_address(adultDTO.getAdult_email_address());
        adult.setAdult_suggested_toys_list("");
        adultRepository.save(adult);
    }

    public void deleteAdult(String adultId) {
        adultRepository.deleteById(Long.parseLong(adultId));
    }

    public List<Adult> getAllAdults() {
        List<Adult> result = new ArrayList<>();
        adultRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Adult user;
        //check if is in database and have correct credential
        user = adultRepository.findByEmail(email);
//
        if (user == null) {
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getAdult_email_address(), user.getAdult_password(),
                new ArrayList<>());
    }

    public void suggestToy(Long toyId, String userName) {
        Child ch = childRepository.findByUsername(userName);
        if (ch != null) {
            String parentId = ch.getChild_parent_id();
            Adult a = adultRepository.findByEmail(parentId);
            if (a != null) {
                StringBuilder list = new StringBuilder(a.getAdult_suggested_toys_list());
                list.append(userName);
                list.append(":");
                list.append(toyId);
                list.append(";");
                if (list.length() > 500) {
                    String[] sug = list.toString().split(";");
                    list = new StringBuilder();
                    for (int i = 1; i < sug.length; i++) {
                        list.append(sug[i]);
                        list.append(";");
                    }
                } else {
                    a.setAdult_suggested_toys_list(list.toString());
                }
                adultRepository.save(a);
            }
        }


    }

    //nie odporny na błędy(których w teorii nie powinnobyć)
    public List<SuggestedToy> getSuggestion(String userName) {
        List<SuggestedToy> sug = new ArrayList<>();
        Adult adult = adultRepository.findByEmail(userName);
        System.out.println("getSuggestion: \n" + adult.toString());
        if (adult.getAdult_suggested_toys_list().equals("") || adult.getAdult_suggested_toys_list() == null) {
            return null;
        }
        for (String s : adult.getAdult_suggested_toys_list().split(";")) {
            String[] oneSuggestion = s.split(":");
            sug.add(new SuggestedToy(childRepository.findByUsername(oneSuggestion[0]), toyRepository.findByIdMY(Long.valueOf(oneSuggestion[1]))));
        }
        return sug;

    }
}
