package com.example.advquerying.services.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {
    private IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Set<Ingredient> findAllByNameStartingWith(String letter) {
        return this.ingredientRepository.findAllByNameStartingWith(letter);

    }

    @Override
    public Set<String> getAllByNameInOrderByPrice(List<String> names) {
        return this.ingredientRepository.getAllByNameInOrderByPrice(names)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteByName(String name) {
        this.ingredientRepository.deleteByName(name);
    }

    @Override
    public void updatePriceBy10percent() {
        this.ingredientRepository.updateByPrice();
    }

    @Override
    public void updatePriceByAmountOnGivenNames(double amount, List<String> names) {
        this.ingredientRepository.updateByPrice(amount, names);
    }
}
