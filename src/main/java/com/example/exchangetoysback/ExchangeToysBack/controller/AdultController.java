package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.SuggestedToy;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "adults")
public class AdultController {
    private final AdultService adultService;

    public AdultController(AdultService adultService) {
        this.adultService = adultService;
    }

    @GetMapping
    public List<Adult> getAdults() {
        return adultService.getAllAdults();
    }

    @PostMapping()
    public ResponseEntity<?> createAdult(@RequestBody AdultDTO adultDTO) {
        Adult adult = adultService.saveAdult(adultDTO);
        if (adult != null)
            return new ResponseEntity<>(adult, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping()
    public void deleteAdult(@RequestParam String adultId) {
        adultService.deleteAdult(adultId);
    }

    @PutMapping()
    public void updateAdult(@RequestBody AdultDTO adultDTO) {
        adultService.updateAdult(adultDTO);
    }

    @GetMapping(value = "suggestion")
    public List<SuggestedToy> getSuggestion() {
        if (TokenInfo.getRole().equals("adult")) {
            return adultService.getSuggestion(TokenInfo.getUserName());
        } else return null;
    }

}
