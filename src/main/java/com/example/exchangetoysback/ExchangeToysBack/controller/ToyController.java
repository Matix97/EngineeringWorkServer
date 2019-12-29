package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.RegisterDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.ToyService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;

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
    public void createToy(@RequestHeader(name = "Authorization") String token, @RequestBody RegisterDTO toyDTO) {//@RequestBody AddToyDTO toyDTO,
        System.out.println("token: " + token);
        System.out.println("username from token: " + TokenInfo.getInstance().getUserName());
        // toyService.createToy(toyDTO);
    }

    @PostMapping(value = "getToys")
    public String getFilterToy() {
        return "getToys";
    }
}
