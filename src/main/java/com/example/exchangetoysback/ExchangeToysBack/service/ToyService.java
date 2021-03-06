package com.example.exchangetoysback.ExchangeToysBack.service;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AddToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.FilterDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.AdultRepository;
import com.example.exchangetoysback.ExchangeToysBack.repository.ToyRepository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ToyService {
    private final ToyRepository toyRepository;
    private final AdultRepository adultRepository;
    private final ChildService childService;

    public ToyService(ToyRepository toyRepository, AdultRepository adultRepository, ChildService childService) {
        this.toyRepository = toyRepository;
        this.adultRepository = adultRepository;
        this.childService = childService;
    }

    public void deleteToy(Long toyID) {
        toyRepository.deleteById(toyID);
    }

    public Toy getById(Long id) {
        //System.out.println("toy service: "+id);
        return toyRepository.findByIdMY(id);
    }

    public void update(Toy toy) {
        toyRepository.save(toy);
    }

    public boolean ifExist(Long id) {
        Toy toy = toyRepository.findByIdMY(id);
        return toy != null;
    }

    public void createToy(AddToyDTO toyDTO) {
        System.out.println("CREATE START: AGE: " + toyDTO.getAgeRange());
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
        toy.setToy_latitude(toyDTO.getToy_latitude());
        toy.setToy_longitude(toyDTO.getToy_longitude());
        toy.setMoney(toyDTO.getMoney());
        toy.setTypOfTransaction(toyDTO.getTypOfTransaction());
        toy.setToy_owner_phone_number(adultRepository.findByEmail(TokenInfo.getUserName()).getAdult_phone_number());
        System.out.println(toy.toString());
        toyRepository.save(toy);
    }

    public List<Toy> getToys() {
        List<Toy> result = new ArrayList<>();
        toyRepository.findAll().forEach(result::add);
        return result;
    }

    public List<Toy> getYourToysAdvert(String email) {
        return new ArrayList<>(toyRepository.findByToy_owner_id(email));

    }

    public List<Toy> getYourRentedToys(String email) {
        return new ArrayList<>(toyRepository.findByToy_current_holder_id(email));

    }


    public List<Toy> getFilterToys(FilterDTO filterDTO) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "____toyService ------START-----");

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
            if (TokenInfo.getRole().equals("child"))
                toy_age_category.addAll(childService.getChildAvailableAge(TokenInfo.getUserName()));
            if (TokenInfo.getRole().equals("adult"))
                toy_age_category.addAll(Arrays.asList(1, 2, 3, 4, 5));

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
        List<Toy> result = new ArrayList<>(toyRepository.findByFilterDTO(main_category, toy_age_category, is_didactic, is_vintage, tag, any_keyword));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "____toyService ------END-----: " + result.size());
        return result;
    }
}
