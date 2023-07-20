package com.example.springdataintro.services.impl;

import com.example.springdataintro.models.entities.User;
import com.example.springdataintro.repositories.UserRepository;
import com.example.springdataintro.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());

        if (byUsername.isPresent()) {
            throw new IllegalArgumentException("User already registered");
        }

        userRepository.save(user);
    }
}
