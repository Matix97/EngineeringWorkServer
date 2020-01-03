package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.SuggestedToy;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="adult")
public class AdultController {
    @Autowired
    private AdultService adultService;

    @GetMapping
    public List<Adult> getAdults() {
        return adultService.getAllAdults();
    }

    //todo add checking if emailAddress is unique
    @PostMapping()
    public void createAdult(@RequestBody AdultDTO adultDTO) {
        adultService.saveAdult(adultDTO);
    }

    @DeleteMapping()
    public void deleteAdult(@RequestParam String adultId) {
        adultService.deleteAdult(adultId);
    }

    @GetMapping(value = "suggestion")
    public List<SuggestedToy> getSuggestion() {
        return adultService.getSuggestion(TokenInfo.getUserName());
    }
}
