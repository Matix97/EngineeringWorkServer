package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AddToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.FilterDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.ToyRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    public List<Toy> getYourToysAdvert(String email) {
        List<Toy> result = new ArrayList<>();
        result.addAll(toyRepository.findByToy_owner_id(email));
        return result;
    }

    public List<Toy> getYourRentedToys(String email) {
        List<Toy> result = new ArrayList<>();
        result.addAll(toyRepository.findByToy_current_holder_id(email));
        return result;
    }


    public List<Toy> getFilterToys(FilterDTO filterDTO) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "____toyService ------START-----");
        List<Toy> result = new ArrayList<>();

        //todo make good query
        String any_keyword = "%%";
        if (filterDTO.getAnyKeyword() != null)
            any_keyword = "%" + filterDTO.getAnyKeyword() + "%";
        //tag
        String tag = "%";
        if (filterDTO.getTags() != null)
            tag = filterDTO.getTags() + ";";
        //main category
        String main_category = "%";
        if (filterDTO.getMainCategory() != null)
            main_category = filterDTO.getMainCategory();
        //toy age category
        List<Integer> toy_age_category = new ArrayList<>();
        Integer iAC = filterDTO.getAge();
        if (iAC != null)
            toy_age_category.add(iAC);
        else {
            toy_age_category.addAll(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
        }
        //didactic
        List<Integer> is_didactic = new ArrayList<>();
        Integer iD = filterDTO.isDidacticNumber();
        if (iD != null)
            is_didactic.add(iD);
        else {
            is_didactic.add(0);
            is_didactic.add(1);
        }
        //vintage
        List<Integer> is_vintage = new ArrayList<>();
        Integer iV = filterDTO.isVintageNumber();
        if (iV != null)
            is_vintage.add(iV);
        else {
            is_vintage.add(0);
            is_vintage.add(1);
        }
        toyRepository.findByFilterDTO(main_category, toy_age_category, is_didactic, is_vintage, tag, any_keyword).forEach(result::add);
        //System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "____toyService after conversion____:\n"+"Main category: "+main_category+ "\nAge category: "+ toy_age_category.toString()
//        +"\nIs didactic: "+is_didactic.toString()+"\nIs vintage: "+is_vintage.toString()+"\ntag: "+tag+"\nAny keyword: "+any_keyword);
//        toyRepository.findByFilterDTO(main_category, toy_age_category, is_didactic, is_vintage, tag, any_keyword).forEach(toy -> {
//            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "____toyService ADDING____:" + toy);
//            result.add(toy);
//        });
        // toyRepository.findByFilterDTObezLiczb(main_category,tag,any_keyword).forEach(result::add);
        //toyRepository.findByFilterFUCK(main_category,tag).forEach(result::add);
        //System.out.println("RESULT SIZE: "+result.size());
        return result;
    }
}
