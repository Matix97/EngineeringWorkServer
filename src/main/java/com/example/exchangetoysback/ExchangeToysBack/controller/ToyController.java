package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AddToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.FilterDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ToyService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "toy")
public class ToyController {

    private final ToyService toyService;

    private final AdultService adultService;

    public ToyController(ToyService toyService, AdultService adultService) {
        this.toyService = toyService;
        this.adultService = adultService;
    }

    @GetMapping
    public List<Toy> getToys() {
        return toyService.getToys();
    }

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
        //  System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " toy/filter");
        if (filterDTO.isPseudoEmpty())
            return toyService.getToys();
        else
            return toyService.getFilterToys(filterDTO);
    }

    @PostMapping(value = "want")
    public void suggestToy(@RequestParam Long toyId) {
        adultService.suggestToy(toyId, TokenInfo.getUserName());
    }
}
