package com.example.springdatademoex.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

public interface AuthorService {
    void seedAuthors() throws IOException;
}
