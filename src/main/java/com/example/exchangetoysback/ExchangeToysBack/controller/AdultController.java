package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.SuggestedToy;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "adult")
public class AdultController {
    private final AdultService adultService;

    public AdultController(AdultService adultService) {
        this.adultService = adultService;
    }

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
        if (TokenInfo.getRole().equals("adult")) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " adult/suggestion:\n " + TokenInfo.getUserName());
            return adultService.getSuggestion(TokenInfo.getUserName());
        } else return null;
    }
}
