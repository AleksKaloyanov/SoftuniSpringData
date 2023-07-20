package com.example.springdatademoex.repositories;

import com.example.springdatademoex.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findBooksByReleaseDateAfter(LocalDate releaseDate);

    Set<Book> findBooksByReleaseDateBefore(LocalDate releaseDate);
}
