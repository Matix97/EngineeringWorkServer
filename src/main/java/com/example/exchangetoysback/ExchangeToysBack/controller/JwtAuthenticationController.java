package com.example.exchangetoysback.ExchangeToysBack.controller;


import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.JwtResponse;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.RegisterDTO;
import com.example.exchangetoysback.ExchangeToysBack.security.JwtTokenUtil;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.JwtUserDetailsService;
import com.example.exchangetoysback.ExchangeToysBack.tools.EncryptionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private ChildService childService;
    @Autowired
    private AdultService adultService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader(value = "Authorization") byte[] message) throws Exception {

        String[] s = EncryptionTools.decrypt(message).split(";");
        String username = s[0];
        String password = s[1];
        String role = s[2];
        if (username == null || password == null || role == null)
            return null;//a niech się wali jak daje złe dane
        System.out.println("Authenticate\n"+username + "\n" + password + "\n" + role);


        //   Adult loggingAdult =null;
        //  Child loggingChild =null;
        if (role.equals("child")) {
//            //check if is in database and have correct credential
//            for (Child ch: childService.getAllChildren()) {
//                if(ch.getChild_name().equals(username)){
//                    loggingChild=ch;
//                    break;
//                }
//            }
//            if(loggingChild!=null)
//            {
//                //check password
//                if(loggingChild.getChild_password().equals(password))
//                {
//                    //generate token
//                }
//                else
//                {
//                    //todo invalid password
//                }
//            }
            authenticate(username, password);

            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));


        } else if (role.equals("adult")) {
            System.out.println("***********************************so adult\t"+username+"\t"+password);
            authenticate(username, password);

            final UserDetails userDetails = adultService.loadUserByUsername(username);

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));


        } else {
            authenticate(username, password);

            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));


        }

    }
boolean flagaTakBrzydkaZeAzStarch=false;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> saveUser(@RequestHeader(value = "Authorization") byte[] message) throws Exception {
        System.out.println("hg");
        String[] s = EncryptionTools.decrypt(message).split(";");
        String username = s[0];
        String password = s[1];
        if (username == null || password == null) {
            return null;//a niech się wali jak daje złe dane
        }
        UserDetails userDetails=null;
        try{
             userDetails= adultService.loadUserByUsername(username);
        }catch(UsernameNotFoundException e){
            flagaTakBrzydkaZeAzStarch=true;
        }

        if(flagaTakBrzydkaZeAzStarch){
        AdultDTO user = new AdultDTO();
        user.setAdult_name("");
        user.setAdult_surname("");
        user.setAdult_password(password);
        user.setAdult_phone_number("");
        user.setAdult_email_address(username);
        adultService.saveAdult(user);
        // return ResponseEntity.ok(jwtUserDetailsService.save(user));
        return ResponseEntity.ok("ok");}
        else
            return ResponseEntity.ok("user exist");


}

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
