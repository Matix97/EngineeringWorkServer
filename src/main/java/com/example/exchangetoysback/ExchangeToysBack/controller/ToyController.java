package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AddToyDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.ToyService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "toy")
public class ToyController {

    @Autowired
    private ToyService toyService;

    @GetMapping
    public List<Toy> getToys() {
        return toyService.getToys();
    }

    @PostMapping()
    public void createToy(@RequestBody AddToyDTO toyDTO) {
        toyService.createToy(toyDTO);
    }

    @PostMapping(value = "getToys")
    public String getFilterToy() {
        return "getToys";
    }
}
