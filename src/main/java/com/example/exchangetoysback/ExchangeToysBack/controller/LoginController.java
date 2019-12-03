package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.Login;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.LoginDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="login")
public class LoginController {
    @Autowired
    private ChildService childService;
    @Autowired
    private AdultService adultService;

    @PostMapping
    public void login(@RequestHeader Login login, @RequestBody String role){
        System.out.println("\n**********************SOMEBODY WANT TO LOGIN******************************\n");
        Adult loggingAdult =null;
        Child loggingChild =null;
        if(role.equals("child")){
            //check if is in database and have correct credential
            for (Child ch: childService.getAllChildren()) {
                if(ch.getChild_name().equals(login.getUsername())){
                    loggingChild=ch;
                    break;
                }
            }
            if(loggingChild!=null)
            {
                //check password
                if(loggingChild.getChild_password().equals(login.getPassword()))
                {
                    //generate token
                }
                else
                {
                    //todo invalid password
                }
            }

        }
        else if(role.equals("adult")){
            //check if is in database and have correct credential
            for (Adult ch: adultService.getAllAdults()) {
                if(ch.getAdult_email_address().equals(login.getUsername())){
                    loggingAdult=ch;
                    break;
                }
            }
            if(loggingAdult!=null)
            {
                //check password
                if(loggingAdult.getAdult_password().equals(login.getPassword()))
                {
                    //generate token
                }
                else
                {
                    //todo invalid password
                }
            }
        }
    }
}
