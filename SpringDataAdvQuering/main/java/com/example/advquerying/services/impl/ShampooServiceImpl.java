package com.example.advquerying.services.impl;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findAllBySizeOrderById(Size size) {
        return this.shampooRepository.findAllBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> findAllBySizeOrLabel_IdOrderByPriceDesc(Size size, Long label_id) {
        return this.shampooRepository.findAllBySizeOrLabel_IdOrderByPrice(size, label_id);
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countAllByPriceIsLessThan(BigDecimal price) {
        return this.shampooRepository.countAllByPriceIsLessThan(price);
    }

    @Override
    public List<String> selectShampoosByIngredients(List<String> ingredients) {
        return this.shampooRepository
                .findAllByIngredientsIn(ingredients)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> selectShampoosByIngredientCount(int count) {
        return this.shampooRepository
                .findAllByIngredientCount(count)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toList());
    }
}
