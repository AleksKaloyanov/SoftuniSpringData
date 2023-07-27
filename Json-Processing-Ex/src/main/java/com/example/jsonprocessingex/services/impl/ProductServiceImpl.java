package com.example.jsonprocessingex.services.impl;

import com.example.jsonprocessingex.models.dtos.ProductNameAndPriceDto;
import com.example.jsonprocessingex.models.dtos.ProductSeedDto;
import com.example.jsonprocessingex.models.entities.Product;
import com.example.jsonprocessingex.repositories.ProductRepository;
import com.example.jsonprocessingex.services.CategoryService;
import com.example.jsonprocessingex.services.ProductService;
import com.example.jsonprocessingex.services.UserService;
import com.example.jsonprocessingex.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.jsonprocessingex.constants.GlobalConstants.RESOURCE_FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_FILE_NAME = "products.json";
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(Gson gson, ValidationUtil validationUtil,
                              ProductRepository productRepository, ModelMapper modelMapper,
                              UserService userService, CategoryService categoryService) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts() throws IOException {
        if (productRepository.count() > 0) {
            return;
        }

        String fileContents =
                Files.readString(Path.of(RESOURCE_FILE_PATH + PRODUCT_FILE_NAME));

        ProductSeedDto[] productSeedDtos = gson.fromJson(fileContents, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.findRandomUser());

                    if (product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0) {
                        product.setBuyer(userService.findRandomUser());
                    }

                    product.setCategories(categoryService.findRandomCategories());

                    return product;
                }).forEach(productRepository::save);
    }

    @Override
    public List<ProductNameAndPriceDto> findAllProductsInRangeAndBuyerIsNullOrderByPrice(BigDecimal lower, BigDecimal upper) {
        return productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(lower, upper)
                .stream()
                .map(product -> {
                    ProductNameAndPriceDto productNameAndPriceDto =
                            modelMapper.map(product, ProductNameAndPriceDto.class);

                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productNameAndPriceDto;
                })
                .collect(Collectors.toList());


    }
}
