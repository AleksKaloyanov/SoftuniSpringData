package com.example.jsonprocessingex.services;

import com.example.jsonprocessingex.models.dtos.UserSoldDto;
import com.example.jsonprocessingex.models.entities.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProduct();
}
