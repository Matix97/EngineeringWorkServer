package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.ChildDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.ChildRepository;
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
public class ChildService implements UserDetailsService {

    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    public void saveChild(ChildDTO childDTO){
        Child child =new Child();
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

    public void deleteChild(String childId){ childRepository.deleteById(Long.parseLong(childId));}

    public List<Child> getAllChildren(){
        List<Child> result = new ArrayList<>();
        childRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Child user = null;
        //check if is in database
        for (Child ch : getAllChildren()) {
            if (ch.getChild_name().equals(username)) {
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

}
