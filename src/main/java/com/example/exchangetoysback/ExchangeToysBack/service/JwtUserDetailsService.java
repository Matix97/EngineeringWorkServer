package com.example.exchangetoysback.ExchangeToysBack.service;
import java.util.ArrayList;

import com.example.exchangetoysback.ExchangeToysBack.controller.DTOmodels.RegisterDTO;
import com.example.exchangetoysback.ExchangeToysBack.repository.UserDao;
import com.example.exchangetoysback.ExchangeToysBack.service.model.DAOUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AdultService adultService;
    @Autowired
    private ChildService childService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPass(),
                new ArrayList<>());
    }

    public void save(RegisterDTO user) {
        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPass(bcryptEncoder.encode(user.getPassword()));
        userDao.save(newUser);
    }
}