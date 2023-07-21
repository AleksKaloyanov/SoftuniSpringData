package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientService {

    Set<Ingredient> findAllByNameStartingWith(String name);

    Set<String> getAllByNameInOrderByPrice(List<String> ingredients);

    void deleteByName(String name);

    void updatePriceBy10percent();

    void updatePriceByAmountOnGivenNames(double amount,List<String> names);

}
