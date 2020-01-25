package com.example.exchangetoysback.ExchangeToysBack.controller;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.ChildDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.ChildUpdateDTO;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.ToyService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import com.example.exchangetoysback.ExchangeToysBack.tools.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "child")
public class ChildController {

    @Autowired
    private ChildService childService;
    @Autowired
    private AdultService adultService;
    @Autowired
    private ToyService toyService;

    @GetMapping
    public List<Child> getChildren() {
        return childService.getMyChildren(TokenInfo.getUserName());
    }

    @GetMapping(value = "my-data")//tylko dla dzieci do filtorwania
    public Child getMyData() {
        return childService.getOneChild(TokenInfo.getUserName());
    }

    @PostMapping("update")
    public Child updateChild(@RequestBody ChildUpdateDTO childDTO) {
        Child ch = childService.getOneChild(childDTO.getChild_login());
        ch.setAvailableTag(childDTO.getAvailableTag());
        ch.setAvailableAge(childDTO.getAvailableAge());
        ch.setChild_radius_area(childDTO.getChild_radius_area());
        ch.setAmountOfSuggesstedToy(childDTO.getAmount());
        childService.update(ch);
        return ch;
    }

    @PostMapping()
    public ResponseEntity<?> createChild(@RequestBody String message) {
        //test
        //System.out.println("childController: " + TokenInfo.getInstance().getUserName() + "given string: " + message.substring(1, message.length() - 1));


        String[] s = message.substring(1, message.length() - 1).split(";");
//        for (String s1 : s) {
//            System.out.println(s1);
//        }

        String name = s[0];
        String login = s[1];
        String password = s[2];
        String childAge = s[3];
        String radius = s[4];
        String age = s[5];
        Integer amount = Integer.valueOf(s[6]);
        if (name == null || password == null || login == null || radius == null || childAge == null || age == null || amount == null) {
            return new ResponseEntity("incorrect data", HttpStatus.BAD_REQUEST);//a niech się wali jak daje złe dane
        }
        System.out.println("przed szukaniem w bazie");
        UserDetails userDetailsCh = childService.loadUserByUsername(login);
        UserDetails userDetailsA = adultService.loadUserByUsername(login);
        if (userDetailsA == null && userDetailsCh == null) {
            //we create child
            System.out.println("we create child");
            ChildDTO childDTO = new ChildDTO();
            childDTO.setChild_name(name);
            childDTO.setChild_login(login);
            childDTO.setChild_password(password);
            childDTO.setChild_age(Integer.parseInt(childAge));
            childDTO.setChild_radius_area(Integer.parseInt(radius));
            childDTO.setChild_parent_id(TokenInfo.getInstance().getUserName());
            childDTO.setAvailableAge(age);
            childDTO.setAmountOfSuggesstedToy(amount);
            System.out.println("Before create child: " + childDTO.toString());
            childService.createChild(childDTO);
            //  System.out.println("RETURN 1");
            return ResponseEntity.ok(childDTO);

        } else {
            //return message that given login is engaged
            //   System.out.println("RETURN 2");
            return new ResponseEntity("given login is busy", HttpStatus.BAD_REQUEST);//one more time stupid, but it will be work fine
        }


    }

    @GetMapping(value = "suggestion")
    public List<Toy> getSuggestion() {
        Child child = childService.getOneChild(TokenInfo.getUserName());
        List<Toy> retList = new ArrayList<>();
        for (String s : child.getChild_suggestion().split(";")) {
            try {
            //    System.out.println("In child COntroller: "+s);
                retList.add(toyService.getById(Long.valueOf(s)));
            } catch (Exception e) {
            }
        }
        return retList;
    }

    @DeleteMapping()
    public void deleteChild(@RequestParam String adultId) {
        childService.deleteChild(adultId);
    }

}
