package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.ChildDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import com.example.exchangetoysback.ExchangeToysBack.tools.EncryptionTools;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "child")
public class ChildController {

    @Autowired
    private ChildService childService;
    @Autowired
    private AdultService adultService;

    @GetMapping
    public List<Child> getChildren() {
        return childService.getAllChildren();
    }

    @PostMapping()  //todo child login must be combination of adult_id/email and child name
    public ResponseEntity<?> createChild(@RequestHeader(value = "Authorization") byte[] message) {
        try {
            String[] s = EncryptionTools.decrypt(message).split(";");
            String name = s[0];
            String login = s[1];
            String password = s[2];
            String childAge = s[3];
            String radius = s[4];
            if (name == null || password == null || login == null || radius == null || childAge == null) {
                return ResponseEntity.ok("incorrect data");//a niech się wali jak daje złe dane
            }
            UserDetails userDetailsCh = childService.loadUserByUsername(login);
            UserDetails userDetailsA = adultService.loadUserByUsername(login);
            if (userDetailsA == null && userDetailsCh == null) {
                //we create child
                ChildDTO childDTO = new ChildDTO();
                childDTO.setChild_name(name);
                childDTO.setChild_login(login);
                childDTO.setChild_password(password);
                childDTO.setChild_age(Integer.parseInt(childAge));
                childDTO.setChild_radius_area(Integer.parseInt(radius));
                childDTO.setChild_parent_id(TokenInfo.getInstance().getUserName());
                childService.saveChild(childDTO);

            } else {
                //return message that given login is engaged
                return ResponseEntity.ok("given login is busy");//one more time stupid, but it will be work fine
            }

        } catch (Exception e) {
        }
        return ResponseEntity.ok("error in decryption");//something wrong.. it's from lazy
    }

    @DeleteMapping()
    public void deleteChild(@RequestParam String adultId) {
        childService.deleteChild(adultId);
    }

}
