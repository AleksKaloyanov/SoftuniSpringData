package com.example.springdatademoex.services.impl;

import com.example.springdatademoex.models.entities.Author;
import com.example.springdatademoex.repositories.AuthorRepository;
import com.example.springdatademoex.services.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of("src/main/resources/files/authors.txt"))
                .forEach(name -> {
                    String[] fullName = name.split("\\s+");
                    authorRepository.save(new Author(fullName[0], fullName[1]));
                });
    }

    @Override
    public Author getRandomAuthor() {
        Long randomId = ThreadLocalRandom.current().nextLong(1, authorRepository.count() + 1);

        return authorRepository.getAuthorById(randomId);
    }





}
