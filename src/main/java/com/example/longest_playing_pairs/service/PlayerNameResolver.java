package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Player;
import com.example.longest_playing_pairs.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerNameResolver {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerNameResolver(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String getPlayerNameById(Long playerId) {
        return playerRepository.findById(playerId)
                .map(Player::getFullName)
                .orElse("Unknown Player");
    }
}
