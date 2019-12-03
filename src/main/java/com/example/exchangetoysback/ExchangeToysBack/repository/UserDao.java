package com.example.exchangetoysback.ExchangeToysBack.repository;
import com.example.exchangetoysback.ExchangeToysBack.service.model.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
}
