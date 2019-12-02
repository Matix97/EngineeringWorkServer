package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.ChildDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.ChildRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    public void saveChild(ChildDTO childDTO){
        Child child =new Child();
        child.setChild_name(childDTO.getChild_name());
        child.setChild_parent_id(childDTO.getChild_parent_id());
        child.setChild_password(childDTO.getChild_password());
        child.setChild_radius_area(childDTO.getChild_radius_area());
        child.setChild_latitude(childDTO.getChild_latitude());
        child.setChild_longitude(childDTO.getChild_longitude());
        childRepository.save(child);
    }

    public void deleteChild(String childId){ childRepository.deleteById(Long.parseLong(childId));}

    public List<Child> getAllChildren(){
        List<Child> result = new ArrayList<>();
        childRepository.findAll().forEach(result::add);
        return result;
    }

}
