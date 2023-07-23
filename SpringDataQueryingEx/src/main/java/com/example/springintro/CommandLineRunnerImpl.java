package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final Scanner sc;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        //printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        //   printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");
        System.out.println("Select exercise number:");
        int exNum = Integer.parseInt(sc.nextLine());
        switch (exNum) {
            case 1 -> bookTitlesByAgeRestriction();
            case 2 -> goldenBooks();
            case 3 -> booksByPrice();
            case 4 -> notReleasedBooks();
            case 5 -> booksReleasedBeforeDate();
            case 6 -> authorsSearch();
            case 7 -> booksSearch();
            case 8 -> bookTitlesSearch();
            case 9 -> countBooks();
            case 10 -> totalBookCopies();
        }


    }

    private void totalBookCopies() {
        this.authorService.findTotalBookCopiesDesc()
                .forEach(System.out::println);
    }

    private void countBooks() {
        System.out.println("Enter a number: ");
        int num = Integer.parseInt(sc.nextLine());
        int count = this.bookService.findCountOfBooksWithTitlesLongerThan(num);
        System.out.println(count);
    }

    private void bookTitlesSearch() {
        System.out.println("Please enter pattern: ");
        String pattern = sc.nextLine();
        this.bookService.findAllWhichAuthorsLastNameStartsWith(pattern)
                .forEach(System.out::println);
    }

    private void booksSearch() {
        System.out.println("Please enter pattern: ");
        String pattern = sc.nextLine();
        this.bookService.findAllBooksContainingPattern(pattern)
                .forEach(System.out::println);
    }

    private void authorsSearch() {
        System.out.println("Please enter pattern: ");
        String pattern = sc.nextLine();
        this.authorService.findAllAuthorsFirstNameEndingWith(pattern)
                .forEach(System.out::println);
    }

    private void booksReleasedBeforeDate() {
        System.out.println("Please enter release date(dd-MM-yyyy): ");
        LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.bookService.findAllBooksReleasedBeforeDate(date)
                .forEach(System.out::println);
    }

    private void notReleasedBooks() {
        System.out.println("Please enter release year: ");
        int year = Integer.parseInt(sc.nextLine());
        this.bookService.findAllThatAreNotReleasedIn(year)
                .forEach(System.out::println);
    }

    private void booksByPrice() {
        this.bookService.findAllByPriceBetween()
                .forEach(book -> {
                    System.out.printf("%s - %.2f%n",
                            book.getTitle(),
                            book.getPrice());
                });
    }


    private void goldenBooks() {
        System.out.println("Please enter the edition type: ");
        String editionType = sc.nextLine().toUpperCase();
        EditionType edition = EditionType.valueOf(editionType);
        System.out.println("Please enter the amount of copies: ");
        Integer copies = Integer.valueOf(sc.nextLine());
        this.bookService.findAllWithEditionTypeAndLessCopiesThan(edition, copies)
                .forEach(System.out::println);
    }

    private void bookTitlesByAgeRestriction() {
        System.out.println("Please enter the age restriction: ");
        String ageRest = sc.nextLine();
        this.bookService
                .findAllByAgeRestrictionIgnoreCase(ageRest)
                .forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
