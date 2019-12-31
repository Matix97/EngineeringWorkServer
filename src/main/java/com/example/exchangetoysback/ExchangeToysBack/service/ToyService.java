package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AddToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.ToyRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToyService {
    @Autowired
    private ToyRepository toyRepository;

    public void createToy(AddToyDTO toyDTO) {
        Toy toy = new Toy();
        toy.setToy_owner_id(TokenInfo.getUserName());
        toy.setToy_name(toyDTO.getName());
        toy.setToy_description(toyDTO.getDescription());
        toy.setToy_age_category(toyDTO.getAgeRange());
        toy.setToy_main_category(toyDTO.getCategory());
        //tags
        StringBuilder tags = new StringBuilder();
        for (String s : toyDTO.getTags()) {
            tags.append(s);
            tags.append(";");
        }
        toy.setToy_special_feature(tags.toString());
        toy.setToy_didactic(toyDTO.isIfDidactic() ? 1 : 0);
        toy.setToy_vintage(toyDTO.isIfVintage() ? 1 : 0);
        //photos

        StringBuilder photos = new StringBuilder();
        for (String s : toyDTO.getPhotosURLs()) {
            photos.append(s);
            photos.append(";");
        }
        toy.setToy_photos(photos.toString());
      //  System.out.println(toy.toString());
        toyRepository.save(toy);
    }

    public List<Toy> getToys() {
        List<Toy> result = new ArrayList<>();
        toyRepository.findAll().forEach(result::add);
        return result;
    }

}
