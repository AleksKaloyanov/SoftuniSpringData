package com.example.springdatademoex.services.impl;

import com.example.springdatademoex.models.entities.Author;
import com.example.springdatademoex.repositories.AuthorRepository;
import com.example.springdatademoex.services.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count()>0){
            return;
        }

        Files.readAllLines(Path.of("src/main/resources/files/authors.txt"))
                .forEach(name -> {
                    String[] fullName = name.split("\\s+");
                    authorRepository.save(new Author(fullName[0], fullName[1]));
                });
    }
}
