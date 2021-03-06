package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AddToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.FilterDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.RentalDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.RentalService;
import com.example.exchangetoysback.ExchangeToysBack.service.ToyService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Rental;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final RentalService rentalService;

    public ToyController(ToyService toyService, AdultService adultService, ChildService childService, RentalService rentalService) {
        this.toyService = toyService;
        this.adultService = adultService;
        this.childService = childService;
        this.rentalService = rentalService;
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


    @DeleteMapping("{id}")
    public void deleteToy(@PathVariable Long id) {
        Toy toy = toyService.getById(id);
        toy.setToy_owner_id("ToyExchangePlatfromDelereAdvert");
        toy.setToy_owner_phone_number(null);
        toyService.update(toy);
        //toyService.deleteToy(id);
    }

    @PostMapping()
    public void createToy(@RequestBody AddToyDTO toyDTO) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " toy/create:\n " + toyDTO.toString());
        toyService.createToy(toyDTO);
    }

    @PostMapping(value = "filter")
    public List<Toy> getFilterToy(@RequestBody FilterDTO filterDTO) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " toy/filter: " + TokenInfo.getRole());
        if (TokenInfo.getRole().equals("child")) {
            if (filterDTO.isPseudoEmpty()) {
                FilterDTO f = new FilterDTO();
                List<Toy> result = toyService.getFilterToys(f);
                result = filterDistance(result, filterDTO.getRadius(), filterDTO.getLatitude(), filterDTO.getLongitude(), "child");
                result = filterName(result);
                return result;
            } else {
                List<Toy> result = toyService.getFilterToys(filterDTO);
                result = filterDistance(result, filterDTO.getRadius(), filterDTO.getLatitude(), filterDTO.getLongitude(), "child");
                result = filterName(result);
                return result;
            }
        } else if (TokenInfo.getRole().equals("adult")) {
            if (filterDTO.getRadius() == null) {
                if (filterDTO.isPseudoEmpty()) {
                    List<Toy> result = toyService.getToys();
                    result = filterName(result);
                    return result;
                } else {
                    List<Toy> result = toyService.getFilterToys(filterDTO);
                    result = filterName(result);
                    return result;
                }

            } else {
                List<Toy> result = toyService.getFilterToys(filterDTO);
                result = filterDistance(result, filterDTO.getRadius(), filterDTO.getLatitude(), filterDTO.getLongitude(), "adult");
                result = filterName(result);
                return result;
            }

        } else
            return null;

    }

    List<Toy> filterName(List<Toy> toyList) {
        List<Toy> finalLis = new ArrayList<>();
        toyList.forEach(toy -> {
            if (!toy.getToy_owner_id().equals("ToyExchangePlatfromDelereAdvert"))
                finalLis.add(toy);
        });
        return finalLis;
    }

    List<Toy> filterDistance(List<Toy> toyList, Integer distance, Double latitude, Double longitude, String role) {
        if (latitude == null || longitude == null)
            return null;
        if (role.equals("child")) {//distance is null
            distance = childService.getRadius();
        }
        Integer finalDistance = distance;
        List<Toy> finalLis = new ArrayList<>();
        toyList.forEach(toy -> {
            //  System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " toy/filter: " + finalDistance + " > " + getDistance(latitude, longitude, toy.getToy_latitude(), toy.getToy_longitude()) + " " + latitude + " " + longitude + " " + toy.getToy_latitude() + " " + toy.getToy_longitude());
            //if (finalDistance > getDistance(latitude, longitude, toy.getToy_latitude(), toy.getToy_longitude()))
            if (finalDistance > getDistance(latitude, longitude, toy.getToy_latitude(), toy.getToy_longitude()))
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
        // System.out.println("BEFORE return: " + distance / 1000 + " points A: " + pointA.toString() + " points B: " + userPos.toString());
        return (int) Math.round(distance / 1000);
    }

    @PostMapping(value = "want")
    public ResponseEntity<?> suggestToy(@RequestBody Long toyId) {
        if (TokenInfo.getRole().equals("child")) {
            System.out.println("want: " + TokenInfo.getUserName());
            Child child = childService.getOneChild(TokenInfo.getUserName());
            String[] sug = child.getChild_suggestion().split(";");

            if (sug.length >= child.getAmountOfSuggesstedToy()) {
                //nie możesz już dodać zabawki

            } else {
                if (thisToyWasInYourFavorite(toyId, child)) {
                    adultService.suggestToy(toyId, TokenInfo.getUserName());
                    String finalSug = child.getChild_suggestion();
                    finalSug += toyId.toString();
                    finalSug += ";";
                    childService.updateSuggestion(child, finalSug);
                    System.out.println("want: GOODDDDDDDD");
                    return new ResponseEntity<>("good", HttpStatus.OK);
                }

            }

        }
        System.out.println("want: BADDDDD");
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private boolean thisToyWasInYourFavorite(Long toyId, Child child) {
        String[] suggestion = child.getChild_suggestion().split(";");
        if (!suggestion[0].equals("")) {
            for (String s : suggestion) {
                if (Long.valueOf(s).equals(toyId))
                    return false;
            }

        }
        return true;
    }

    @DeleteMapping(value = "want/{id}")
    public ResponseEntity<Long> deleteSuggestToy(@PathVariable Long id) {
        boolean isRemoved = deleteSuggestion(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    private boolean deleteSuggestion(Long id) {
        Child child = childService.getOneChild(TokenInfo.getUserName());
        Adult adult = adultService.getOneAdult(child.getChild_parent_id());
        String[] childSug = child.getChild_suggestion().split(";");
        StringBuilder finalChildSug = new StringBuilder();
        for (String s : childSug) {
            if (!s.equals(id.toString())) {
                finalChildSug.append(s);
                finalChildSug.append(";");
            }
        }
        child.setChild_suggestion(finalChildSug.toString());

        String[] adultSug = adult.getAdult_suggested_toys_list().split(";");
        StringBuilder finalAdultSug = new StringBuilder();
        String toCompare = TokenInfo.getUserName() + ":" + id.toString();
        // System.out.println("To comparre: "+toCompare);
        for (String s : adultSug) {
            //   System.out.println("Compared: "+s);
            if (!s.equals(toCompare)) {
                finalAdultSug.append(s);
                finalAdultSug.append(";");
            }
        }
        adult.setAdult_suggested_toys_list(finalAdultSug.toString());

        childService.update(child);
        adultService.update(adult);

        return true;
    }

    @PostMapping(value = "rent")
    public ResponseEntity<?> rentToy(@RequestBody RentalDTO rentalDTO) {
        if (TokenInfo.getRole().equals("adult")) {

            boolean everythingIsOk = false;
            System.out.println("freeTimeRental");
            Rental rental = rentalService.creat(rentalDTO, rentalDTO.getToyIdTo());
            if (rental != null) {
                Toy toy = toyService.getById(rentalDTO.getToyIdTo());
                toy.setToy_current_holder_id(rentalDTO.getFutureHolderEmail());
                toyService.update(toy);
                everythingIsOk = true;
            }


            if (everythingIsOk)
                return new ResponseEntity<>("good", HttpStatus.OK);
            else
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
