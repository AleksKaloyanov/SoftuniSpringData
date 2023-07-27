package com.example.jsonprocessingex.services;

import com.example.jsonprocessingex.models.dtos.ProductNameAndPriceDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductNameAndPriceDto> findAllProductsInRangeAndBuyerIsNullOrderByPrice(BigDecimal lower, BigDecimal upper);
}
