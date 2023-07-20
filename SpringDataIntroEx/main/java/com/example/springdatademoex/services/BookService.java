package com.example.springdatademoex.services;

import com.example.springdatademoex.models.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getBooksTitlesReleasedAfterYear(int year);

    List<String > getAuthorFullNameWithBookBeforeYear(int year);
}
