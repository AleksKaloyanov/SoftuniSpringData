package com.example.automappingobjectex.services;

import com.example.automappingobjectex.models.dtos.UserLoginDto;
import com.example.automappingobjectex.models.dtos.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logoutUser();
}
