package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {

    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabel_IdOrderByPriceDesc(Size size, Long label_id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceIsLessThan(BigDecimal price);

    List<String> selectShampoosByIngredients(List<String> ingredients);

    List<String > selectShampoosByIngredientCount(int count);
}
