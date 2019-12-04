package com.example.exchangetoysback.ExchangeToysBack.controller;


import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.JwtRequest;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.JwtResponse;
import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.UserDTO;
import com.example.exchangetoysback.ExchangeToysBack.security.JwtTokenUtil;
import com.example.exchangetoysback.ExchangeToysBack.service.JwtUserDetailsService;
import com.example.exchangetoysback.ExchangeToysBack.service.model.DAOUser;
import com.example.exchangetoysback.ExchangeToysBack.tools.EncryptionTools;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader(value="Authorization") byte[] message) throws Exception {
        System.out.println("message: "+message);
        System.out.println("String(message): "+new String(message));
        String [] s= EncryptionTools.decrypt(message).split(";");
        String username=s[0];
        String password=s[1];
        String role=s[2];
        if(username==null || password==null || role==null)
            return null;//a niech się wali jak daje złe dane
        System.out.println(username+"\n"+password+"\n"+role);

        authenticate(username, password);

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> saveUser(@RequestBody UserDTO user) throws Exception {
        System.out.println("strart");
        jwtUserDetailsService.save(user);
       // return ResponseEntity.ok(jwtUserDetailsService.save(user));
        return ResponseEntity.ok("ok");
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
