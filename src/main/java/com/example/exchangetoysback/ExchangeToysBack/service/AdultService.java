package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.AdultRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdultService implements UserDetailsService {
    @Autowired
    private AdultRepository adultRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;


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
    public UserDetails loadUserByUsername(String email)  {
        Adult user = null;

        System.out.println("Kurwa 1");
        //check if is in database and have correct credential
        for (Adult ch : getAllAdults()) {
            System.out.println("Kurwa 2");
            if (ch.getAdult_email_address().equals(email)) {
                System.out.println(ch.toString());
                user = ch;
                break;
            }
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getAdult_email_address(), user.getAdult_password(),
                new ArrayList<>());
    }
}
