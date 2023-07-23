package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal lowerBound, BigDecimal upperBound);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate releaseDateBefore, LocalDate releaseDateAfter);

    List<Book> findAllByTitleContainingIgnoreCase(String pattern);

    @Query("Select b from Book b where lower(b.author.lastName) like lower(concat(:author,'%'))")
    List<Book> findAllByAuthor_LastNameStartsWithIgnoreCasing(String author);

    @Query("Select b from Book b group by b.id having length(b.title)> :length ")
    List<Book> findAllByTitleLength(int length);



}
