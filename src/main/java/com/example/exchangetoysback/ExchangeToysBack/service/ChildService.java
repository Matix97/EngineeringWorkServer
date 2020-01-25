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

    public void createChild(ChildDTO childDTO) {
        Child child = new Child();
        child.setChild_name(childDTO.getChild_name());
        child.setChild_parent_id(childDTO.getChild_parent_id());
        child.setChild_password(bcryptEncoder.encode(childDTO.getChild_password()));
        child.setChild_radius_area(childDTO.getChild_radius_area());
        child.setChild_latitude(childDTO.getChild_latitude());
        child.setChild_longitude(childDTO.getChild_longitude());
        child.setChild_login(childDTO.getChild_login());
        child.setChild_age(childDTO.getChild_age());
        child.setChild_suggestion("");
        child.setAvailableAge(childDTO.getAvailableAge());
        child.setAvailableTag("soft;funny;scary;groupToy;collector;boys;girls");
        child.setAmountOfSuggesstedToy(childDTO.getAmountOfSuggesstedToy());
        childRepository.save(child);
    }


    public void deleteChild(String childId) {
        childRepository.deleteById(Long.parseLong(childId));
    }

    public void updateSuggestion(Child child, String suggestions) {
        child.setChild_suggestion(suggestions);
        childRepository.save(child);
    }

    public void update(Child ch) {
        childRepository.save(ch);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Child user;
        //check if is in database
        user = childRepository.findByUsername(username);

        if (user == null) {
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getChild_login(), user.getChild_password(),
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

    public List<Integer> getChildAvailableAge(String login) {
        Child child = getOneChild(login);
        ArrayList<Integer> ret = new ArrayList<>();
        for (String s : child.getAvailableAge().split(";")) {
            ret.add(getAgeParsed(s));
        }
        return ret;
    }

    public Integer getAgeParsed(String age) {
        if (age == null)
            return null;
        switch (age) {
            case "0-3":
                return 1;
            case "4-7":
                return 2;
            case "8-12":
                return 3;
            case "13-15":
                return 4;
            case "16-100":
                return 5;
        }
        return null;
    }

}
