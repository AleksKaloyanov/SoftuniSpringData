package com.example.springdatademoex.services.impl;

import com.example.springdatademoex.models.entities.*;
import com.example.springdatademoex.repositories.AuthorRepository;
import com.example.springdatademoex.repositories.BookRepository;
import com.example.springdatademoex.repositories.CategoryRepository;
import com.example.springdatademoex.services.BookService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorService;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository, AuthorServiceImpl authorService, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryRepository = categoryRepository;
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

        Set<Category> categories = getRandomCategories();

        return new Book(title, editionType, price, copies, date,
                ageRestriction, author, categories);

    }

    private Set<Category> getRandomCategories() {
        Random random = new Random();

        Set<Category> set = new HashSet<>();

        int rnd = random.nextInt(1, 3);

        for (int i = 0; i < rnd; i++) {
            Long rndId = ThreadLocalRandom.current()
                    .nextLong(1, categoryRepository.count() + 1);

            set.add(categoryRepository
                    .findById(rndId)
                    .orElse(null));
        }

        return set;
    }
}
