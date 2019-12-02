package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.ChildDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="child")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping
    public List<Child> getAdults(){ return childService.getAddChildren();}

    @PostMapping()
    public void createAdult(@RequestBody ChildDTO childDTO){childService.saveChild(childDTO);}

    @DeleteMapping()
    public void deleteAdult(@RequestParam String adultId){childService.deleteChild(adultId);}

}
