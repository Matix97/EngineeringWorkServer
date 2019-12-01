package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="adult")
public class AdultController {
    @Autowired
    private AdultService adultService;

    @GetMapping
    public List<Adult> getAdults(){ return adultService.getAddAdults();}

    @PostMapping()
    public void createAdult(@RequestBody AdultDTO adultDTO){adultService.saveAdult(adultDTO);}

    @DeleteMapping()
    public void deleteAdult(@RequestParam String adultId){adultService.deleteAdult(adultId);}

}
