package com.example.springdatademoex.services;

import com.example.springdatademoex.models.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

}
