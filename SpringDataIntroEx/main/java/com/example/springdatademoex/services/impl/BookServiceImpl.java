package com.example.springdatademoex.services.impl;

import com.example.springdatademoex.models.entities.*;
import com.example.springdatademoex.repositories.BookRepository;
import com.example.springdatademoex.services.BookService;
import com.example.springdatademoex.services.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorServiceImpl authorService, CategoryService categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of("src/main/resources/files/books.txt"))
                .stream()
                .forEach(row -> {
                    Book book = createBook(row);

                    bookRepository.save(book);
                });

    }

    @Override
    public List<Book> getBooksTitlesReleasedAfterYear(int year) {
        return bookRepository.findBooksByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> getAuthorFullNameWithBookBeforeYear(int year) {
        return bookRepository.findBooksByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book ->
                        String.format("%s %s",
                                book.getAuthor().getFirstName(),
                                book.getAuthor().getLastName())

                ).distinct()
                .collect(Collectors.toList());
    }

    private Book createBook(String row) {
        String[] bookInfo = row.split("\\s+");
        EditionType editionType =
                EditionType.values()[Integer.parseInt(bookInfo[0])];

        LocalDate date = LocalDate.parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(bookInfo[3]));

        AgeRestriction ageRestriction =
                AgeRestriction.values()[Integer.parseInt(bookInfo[4])];

        String title = Arrays
                .stream(row.split("\\s+"))
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();

        Set<Category> categories = categoryService.getRandomCategories();

        return new Book(title, editionType, price, copies, date,
                ageRestriction, author, categories);

    }
}
