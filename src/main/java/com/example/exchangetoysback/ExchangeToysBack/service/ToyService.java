package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.ToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.ToyRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToyService {
    @Autowired
    private ToyRepository toyRepository;

    public void createToy(ToyDTO toyDTO){
        Toy toy = new Toy();
        toy.setToy_owner_id(toyDTO.getToy_owner_id());
        toy.setToy_name(toyDTO.getToy_name());
        toy.setToy_description(toyDTO.getToy_description());
        toy.setToy_age_category(toyDTO.getToy_age_category());
        toy.setToy_main_category(toyDTO.getToy_main_category());
        toy.setToy_special_feature(toyDTO.getToy_special_feature());
        toy.setToy_tags(toyDTO.getToy_tags());
        toy.setToy_didactic(toyDTO.getToy_didactic());
        toy.setToy_vintage(toyDTO.getToy_vintage());
        toy.setToy_factory_name(toyDTO.getToy_factory_name());
        toy.setToy_quality_of_made(toyDTO.getToy_quality_of_made());
        toy.setToy_photos(toyDTO.getToy_photos());
        toyRepository.save(toy);
    }
    public List<Toy> getToys(){
        List<Toy> result = new ArrayList<>();
        toyRepository.findAll().forEach(result::add);
        return result;
    }

}
