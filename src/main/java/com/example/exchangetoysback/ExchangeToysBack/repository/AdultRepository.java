package com.example.exchangetoysback.ExchangeToysBack.repository;

import com.example.exchangetoysback.ExchangeToysBack.service.model.Adult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AdultRepository extends CrudRepository<Adult,Long> {

}
