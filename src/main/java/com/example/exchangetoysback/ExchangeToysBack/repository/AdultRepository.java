package com.example.exchangetoysback.ExchangeToysBack.repository;

import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdultRepository extends CrudRepository<Adult,String> {

}
