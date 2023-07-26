package com.example.automappingobjectex;

import com.example.automappingobjectex.models.dtos.UserLoginDto;
import com.example.automappingobjectex.models.dtos.UserRegisterDto;
import com.example.automappingobjectex.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private BufferedReader br;
    private final UserService userService;

    public CommandLineRunnerImpl(UserService userService) {
        this.userService = userService;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("Please enter your command: ");
            String[] commands = br.readLine().split("\\|");
            switch (commands[0]) {
                case "RegisterUser" -> userService.registerUser(new UserRegisterDto(commands[1], commands[2],
                        commands[3], commands[4]));
                case "LoginUser" -> userService.loginUser(new UserLoginDto(commands[1], commands[2]));
                case "Logout" -> userService.logoutUser();

            }
        }
    }
}

