package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Player;
import com.example.longest_playing_pairs.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerTeamResolver {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerTeamResolver(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String getPlayerTeamById(Long playerId) {
        return playerRepository.findById(playerId)
                .map(Player::getTeamID)
                .map(String::valueOf)
                .orElse("Unknown Team");
    }
}
