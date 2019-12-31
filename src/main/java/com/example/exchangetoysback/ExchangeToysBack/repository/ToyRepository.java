package com.example.exchangetoysback.ExchangeToysBack.repository;

import com.example.exchangetoysback.ExchangeToysBack.service.model.Toy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToyRepository extends CrudRepository<Toy, Long> {
    @Query("FROM Toy WHERE toy_owner_id = ?1")
    List<Toy> findByToy_owner_id(String firstName);

    @Query("FROM Toy WHERE toy_current_holder_id = ?1")
    List<Toy> findByToy_current_holder_id(String firstName);

    @Query("FROM Toy  WHERE toy_main_category = ?1 AND toy_age_category = ?2 AND toy_didactic = ?3 AND toy_vintage = ?4")
    List<Toy> findByFirstNameAndLastName(String toy_main_category, Integer toy_age_category, Integer toy_didactic, Integer toy_vintage);
}
