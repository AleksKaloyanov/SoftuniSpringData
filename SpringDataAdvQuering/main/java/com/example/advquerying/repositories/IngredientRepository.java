package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Set<Ingredient> findAllByNameStartingWith(String letter);

    List<Ingredient> getAllByNameInOrderByPrice(List<String> names);

    @Query("delete from Ingredient i where i.name = :name")
    @Modifying
    @Transactional
    void deleteByName(String name);

    @Query("update Ingredient i set i.price = i.price*1.10")
    @Modifying
    @Transactional
    void updateByPrice();

    @Query("update Ingredient i set i.price = i.price* :amount where i.name in :names")
    @Modifying
    @Transactional
    void updateByPrice(double price, List<String> names);
}
