package com.example.jsonprocessingex.services.impl;

import com.example.jsonprocessingex.models.dtos.CategorySeedDto;
import com.example.jsonprocessingex.models.entities.Category;
import com.example.jsonprocessingex.repositories.CategoryRepository;
import com.example.jsonprocessingex.services.CategoryService;
import com.example.jsonprocessingex.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.jsonprocessingex.constants.GlobalConstants.RESOURCE_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_FILE_NAME = "categories.json";
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(Gson gson, ValidationUtil validationUtil,
                               CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        String fileContent = Files
                .readString(Path.of(RESOURCE_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDto[] categorySeedDto = gson.fromJson(fileContent, CategorySeedDto[].class);

        Arrays.stream(categorySeedDto)
                .filter(validationUtil::isValid)
                .map(c -> modelMapper.map(c, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categorySet = new HashSet<>();
        int catCount = ThreadLocalRandom
                .current().nextInt(1, 3);
        long totalCount = categoryRepository.count();

        for (int i = 0; i < catCount; i++) {
            long randomId = ThreadLocalRandom
                    .current().nextLong(1, totalCount + 1);

            categorySet.add(categoryRepository.findById(randomId)
                    .orElse(null));
        }

        return categorySet;
    }
}
