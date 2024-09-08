package com.example.longest_playing_pairs.repository;

import com.example.longest_playing_pairs.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Optional<Player> findById(Long id);
}
