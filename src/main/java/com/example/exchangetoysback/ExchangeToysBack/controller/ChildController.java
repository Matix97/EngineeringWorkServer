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
    public List<Child> getChildren(){ return childService.getAllChildren();}

    @PostMapping()  //todo child login must be combination of adult_id/email and child name
    public void createChild(@RequestBody ChildDTO childDTO){childService.saveChild(childDTO);}

    @DeleteMapping()
    public void deleteChild(@RequestParam String adultId){childService.deleteChild(adultId);}

}
