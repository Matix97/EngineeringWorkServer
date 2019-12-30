package com.example.exchangetoysback.ExchangeToysBack.controller;


import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.AdultDTO;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.JwtResponse;
import com.example.exchangetoysback.ExchangeToysBack.security.JwtTokenUtil;
import com.example.exchangetoysback.ExchangeToysBack.service.AdultService;
import com.example.exchangetoysback.ExchangeToysBack.service.ChildService;
import com.example.exchangetoysback.ExchangeToysBack.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestHeader(value = "Authorization") String message) throws Exception {
        System.out.println("Auth: " + message);
        String[] s = message.split(";");
        String email = s[0];
        String password = s[1];
        String role = s[2];
        if (email == null || password == null || role == null)
            return null;//a niech się wali jak daje złe dane
        System.out.println("Authenticate\n" + email + "\t" + password + "\t" + role);
        authenticate(email, password);

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
        System.out.println(userDetails.getUsername() + " " + userDetails.getPassword());
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("Token: " + token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new JwtResponse(token));


    }

    //todo dojdzie więcej parametrów jak po stronie andorida sdię doda
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> saveUser(@RequestHeader(value = "Authorization") String message) throws Exception {
        String[] s = message.split(";");
        String name = s[0];
        String surname = s[1];
        String password = s[2];
        String phoneNumber = s[3];
        String email = s[4];
        if (name == null || password == null || surname == null || email == null) {
            return null;//a niech się wali jak daje złe dane
        }
        UserDetails userDetailsA = adultService.loadUserByUsername(email);
        UserDetails userDetailsCh = childService.loadUserByUsername(email);


        if (userDetailsA == null && userDetailsCh == null) {
            AdultDTO user = new AdultDTO();
            user.setAdult_name(name);
            user.setAdult_surname(surname);
            user.setAdult_password(password);
            user.setAdult_phone_number(phoneNumber);
            user.setAdult_email_address(email);
            adultService.saveAdult(user);
            return ResponseEntity.ok("ok");
        } else
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
