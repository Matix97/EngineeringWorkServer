package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AddToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.FilterDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.ToyService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "toy")
public class ToyController {

    private final ToyService toyService;

    private final AdultService adultService;

    private final ChildService childService;

    public ToyController(ToyService toyService, AdultService adultService, ChildService childService) {
        this.toyService = toyService;
        this.adultService = adultService;
        this.childService = childService;
    }

//    @GetMapping
//    public List<Toy> getToys() {
//        return toyService.getToys();
//    }

    @GetMapping(value = "yourAdvert")
    public List<Toy> getYourAdvertToys() {
        return toyService.getYourToysAdvert(TokenInfo.getUserName());
    }

    @GetMapping(value = "yourRentedToy")
    public List<Toy> getYourRentedToys() {
        return toyService.getYourRentedToys(TokenInfo.getUserName());
    }


    @PostMapping()
    public void createToy(@RequestBody AddToyDTO toyDTO) {
        toyService.createToy(toyDTO);
    }

    @PostMapping(value = "filter")
    public List<Toy> getFilterToy(@RequestBody FilterDTO filterDTO) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " toy/filter: " + TokenInfo.getRole());
        if (TokenInfo.getRole().equals("child")) {
            if (filterDTO.isPseudoEmpty()) {
                List<Toy> result = toyService.getToys();
                result = filterDistance(result, filterDTO.getRadius(), filterDTO.getLatitude(), filterDTO.getLongitude(), "child");
                return result;
            } else {
                List<Toy> result = toyService.getFilterToys(filterDTO);
                result = filterDistance(result, filterDTO.getRadius(), filterDTO.getLatitude(), filterDTO.getLongitude(), "child");
                return result;
            }
        } else if (TokenInfo.getRole().equals("adult")) {
            if (filterDTO.getRadius() == null) {
                if (filterDTO.isPseudoEmpty())
                    return toyService.getToys();
                else
                    return toyService.getFilterToys(filterDTO);
            } else {
                List<Toy> result = toyService.getFilterToys(filterDTO);
                result = filterDistance(result, filterDTO.getRadius(), filterDTO.getLatitude(), filterDTO.getLongitude(), "adult");
                return result;
            }

        } else
            return null;

    }

    List<Toy> filterDistance(List<Toy> toyList, Integer distance, double latitude, double longitude, String role) {

        if (role.equals("child")) {//distance is null
            distance = childService.getRadius();
        }
        Integer finalDistance = distance;
        toyList.forEach(toy -> {
            if (finalDistance < getDistance(latitude, longitude, toy.getToy_latitude(), toy.getToy_longitude()))
                toyList.remove(toy);
        });
        return toyList;
    }

    private int getDistance(double lat1, double lon1, double lat2, double lon2) {

        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return (int) Math.round(d);
    }

    @PostMapping(value = "want")
    public void suggestToy(@RequestBody Long toyId) {
        adultService.suggestToy(toyId, TokenInfo.getUserName());
    }
}
