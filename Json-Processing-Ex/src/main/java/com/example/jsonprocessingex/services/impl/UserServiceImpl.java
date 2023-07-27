package com.example.jsonprocessingex.services.impl;

import com.example.jsonprocessingex.models.dtos.UserSeedDto;
import com.example.jsonprocessingex.models.dtos.UserSoldDto;
import com.example.jsonprocessingex.models.entities.User;
import com.example.jsonprocessingex.repositories.UserRepository;
import com.example.jsonprocessingex.services.UserService;
import com.example.jsonprocessingex.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.jsonprocessingex.constants.GlobalConstants.RESOURCE_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_NAME = "users.json";
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(Gson gson, ValidationUtil validationUtil,
                           UserRepository userRepository, ModelMapper modelMapper) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() == 0) {
            Arrays.stream(gson.fromJson(
                            Files.readString(
                                    Path.of(RESOURCE_FILE_PATH + USERS_FILE_NAME)
                            ), UserSeedDto[].class
                    )).filter(validationUtil::isValid)
                    .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                    .forEach(userRepository::save);
        }
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, userRepository.count() + 1);

        return userRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUsersWithMoreThanOneSoldProduct() {
        return userRepository
                .findAllUsersWithMoreThanOneSoldProductOrOrderByLastNameThenByFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }
}
