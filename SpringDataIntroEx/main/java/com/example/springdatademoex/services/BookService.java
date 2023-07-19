package com.example.springdatademoex.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

public interface BookService {
    void seedBooks() throws IOException;
}
