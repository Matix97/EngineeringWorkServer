package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AddToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.FilterDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.ToyService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        List<Toy> finalLis = new ArrayList<>();
        toyList.forEach(toy -> {
            // System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " toy/filter: " + finalDistance + " > "+ getDistance(latitude, longitude, toy.getToy_latitude(), toy.getToy_longitude()) + " "+latitude+ " "+ longitude+ " "+ toy.getToy_latitude()+ " "+ toy.getToy_longitude());
            //if (finalDistance > getDistance(latitude, longitude, toy.getToy_latitude(), toy.getToy_longitude()))
            if (finalDistance > getDistance(51.8746158, 19.3622803, toy.getToy_latitude(), toy.getToy_longitude()))
                finalLis.add(toy);
        });
        return finalLis;
    }

    private int getDistance(double lat1, double lon1, double lat2, double lon2) {

        GeodeticCalculator geoCalc = new GeodeticCalculator();
        Ellipsoid reference = Ellipsoid.WGS84;
        GlobalPosition pointA = new GlobalPosition(lat1, lon1, 0.0); // Point A
        GlobalPosition userPos = new GlobalPosition(lat2, lon2, 0.0); // Point B
        double distance = geoCalc.calculateGeodeticCurve(reference, userPos, pointA).getEllipsoidalDistance(); // Distance between Point A and Point B
        //  System.out.println("AFTER return: "+ (int) Math.round(distance));
        return (int) Math.round(distance / 1000);
    }

    @PostMapping(value = "want")
    public void suggestToy(@RequestBody Long toyId) {
        adultService.suggestToy(toyId, TokenInfo.getUserName());
    }
}
