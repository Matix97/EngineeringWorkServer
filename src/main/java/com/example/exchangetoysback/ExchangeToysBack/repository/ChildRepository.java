package com.example.exchangetoysback.ExchangeToysBack.repository;

import com.example.exchangetoysback.ExchangeToysBack.service.model.Child;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends CrudRepository<Child, Long> {
    @Query("FROM Child WHERE child_parent_id = ?1")
    List<Child> findByChild_parent_id(String child_parent_id);

    @Query("FROM Child WHERE child_login = ?1")
    Child findByUsername(String userName);
}
