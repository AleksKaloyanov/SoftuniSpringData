package com.example.automappingobjectex.services.impl;

import com.example.automappingobjectex.repositories.GameRepository;
import com.example.automappingobjectex.services.GameService;

public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
