package com.example.springdatademoex.init;

import com.example.springdatademoex.services.AuthorService;
import com.example.springdatademoex.services.BookService;
import com.example.springdatademoex.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
//        printBookTitlesReleasedAfterYear(2000);
//        printAuthorFullNameWithBookBeforeYear(1990);

    }

    private void printAuthorFullNameWithBookBeforeYear(int year) {
        bookService.getAuthorFullNameWithBookBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printBookTitlesReleasedAfterYear(int year) {
        bookService.getBooksTitlesReleasedAfterYear(year)
                .forEach(b -> {
                    System.out.println(b.getTitle());
                });
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
