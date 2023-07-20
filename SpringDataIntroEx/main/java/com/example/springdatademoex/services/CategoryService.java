package com.example.springdatademoex.services;

import com.example.springdatademoex.models.entities.Category;

import java.io.IOException;
import java.util.Set;


public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
