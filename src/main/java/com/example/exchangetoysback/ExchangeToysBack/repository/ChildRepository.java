package com.example.exchangetoysback.ExchangeToysBack.repository;

import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends CrudRepository<Child,Long> {
}
