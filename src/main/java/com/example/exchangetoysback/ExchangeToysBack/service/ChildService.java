package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.ChildDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.ChildRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChildService implements UserDetailsService {

    private final ChildRepository childRepository;
    private final PasswordEncoder bcryptEncoder;

    public ChildService(ChildRepository childRepository, PasswordEncoder bcryptEncoder) {
        this.childRepository = childRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    public void saveChild(ChildDTO childDTO) {
        Child child = new Child();
        child.setChild_name(childDTO.getChild_name());
        child.setChild_parent_id(childDTO.getChild_parent_id());
        child.setChild_password(bcryptEncoder.encode(childDTO.getChild_password()));
        child.setChild_radius_area(childDTO.getChild_radius_area());
        child.setChild_latitude(childDTO.getChild_latitude());
        child.setChild_longitude(childDTO.getChild_longitude());
        child.setChild_login(childDTO.getChild_login());
        child.setChild_age(childDTO.getChild_age());
        childRepository.save(child);
    }

    public void deleteChild(String childId) {
        childRepository.deleteById(Long.parseLong(childId));
    }

    public List<Child> getAllChildren() {
        List<Child> result = new ArrayList<>();
        childRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Child user = null;
        //check if is in database
        for (Child ch : getAllChildren()) {
            if (ch.getChild_login().equals(username)) {
                //  System.out.println(ch.toString());
                user = ch;
                break;
            }
        }
        if (user == null) {
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getChild_name(), user.getChild_password(),
                new ArrayList<>());
    }

    public List<Child> getMyChildren(String parentEmail) {
        return new ArrayList<>(childRepository.findByChild_parent_id(parentEmail));

    }

    public Integer getRadius() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " toy/filter: " + TokenInfo.getUserName());
        return childRepository.findByUsername(TokenInfo.getUserName()).getChild_radius_area();
    }

    public Child getOneChild(String login) {
        return childRepository.findByUsername(login);
    }
}
