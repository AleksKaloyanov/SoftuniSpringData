package com.example.springdatademoex.services.impl;

import com.example.springdatademoex.models.entities.Category;
import com.example.springdatademoex.repositories.CategoryRepository;
import com.example.springdatademoex.services.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of("src/main/resources/files/categories.txt"))
                .stream()
                .filter(cat -> !cat.isBlank())
                .forEach(category -> {
                    categoryRepository.save(new Category(category));
                });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();

        Set<Category> set = new HashSet<>();

        int rnd = random.nextInt(1, 3);

        long catRepoCount = categoryRepository.count();

        for (int i = 0; i <= rnd; i++) {
            Long rndId = ThreadLocalRandom.current()
                    .nextLong(1, catRepoCount + 1);

            Category category = categoryRepository.getCategoryById(rndId);

            set.add(category);
        }

        return set;
    }
}
