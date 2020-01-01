package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.AdultRepository;
import com.example.exchangetoysback.ExchangeToysBack.repository.ChildRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
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
    private ChildRepository childRepository;

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Adult user = null;
        //check if is in database and have correct credential
        for (Adult ch : getAllAdults()) {
            if (ch.getAdult_email_address().equals(email)) {
                //  System.out.println(ch.toString());
                user = ch;
                break;
            }
        }
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
                String list = a.getAdult_suggested_toys_list();
                list += userName;
                list += ":";
                list += toyId;
                list += ";";
                if (list.length() > 500) {
                    String[] sug = list.split(";");
                    list = "";
                    for (int i = 1; i < sug.length; i++) {
                        list += sug[i];
                        list += ";";
                    }
                } else {
                    a.setAdult_suggested_toys_list(list);
                }
                adultRepository.save(a);
            }
        }


    }
}
