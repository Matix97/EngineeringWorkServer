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

    @Query("FROM Toy  WHERE toy_main_category LIKE ?1 AND toy_age_category IN ?2 AND toy_didactic IN ?3 AND toy_vintage IN ?4 AND toy_special_feature LIKE ?5 AND (toy_main_category LIKE ?6 OR toy_special_feature LIKE ?6 OR toy_name LIKE ?6 OR toy_factory_name LIKE ?6 OR toy_description LIKE ?6)")
    List<Toy> findByFilterDTO(String toy_main_category, List<Integer> toy_age_category, List<Integer> toy_didactic, List<Integer> toy_vintage, String tag, String any_keyword);


}
