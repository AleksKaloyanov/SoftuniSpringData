package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String > findAllByAgeRestrictionIgnoreCase(String  ageRestriction);

    List<String > findAllWithEditionTypeAndLessCopiesThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceBetween();

    List<String> findAllThatAreNotReleasedIn(int year);

    List<String> findAllBooksReleasedBeforeDate(LocalDate date);

    List<String> findAllBooksContainingPattern(String pattern);

    List<String> findAllWhichAuthorsLastNameStartsWith(String pattern);

    int findCountOfBooksWithTitlesLongerThan(int num);

}
