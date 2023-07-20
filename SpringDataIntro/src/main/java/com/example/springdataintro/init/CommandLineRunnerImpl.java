package com.example.springdataintro.init;

import com.example.springdataintro.services.AccountService;
import com.example.springdataintro.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private UserService userService;
    private AccountService accountService;

    public CommandLineRunnerImpl(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
//        User user = new User("Pesho", 20);
//
//        userService.registerUser(user);

//        Account account = new Account(new BigDecimal("25000"));
//        account.setUser(user);

//        user.setAccounts(new HashSet<>() {{
//            add(account);
//        }});


        accountService.withdrawMoney(new BigDecimal("20000"), 1L);
        accountService.depositMoney(new BigDecimal("30000"), 1L);
    }
}
