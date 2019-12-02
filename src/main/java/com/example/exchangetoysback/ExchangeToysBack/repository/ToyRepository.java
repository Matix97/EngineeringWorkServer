package com.example.exchangetoysback.ExchangeToysBack.repository;

import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToyRepository extends CrudRepository<Toy,Long> {
}
