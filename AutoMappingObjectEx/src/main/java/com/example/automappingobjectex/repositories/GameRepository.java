package com.example.automappingobjectex.repositories;

import com.example.automappingobjectex.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long> {
}
