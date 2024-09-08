package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Player;
import com.example.longest_playing_pairs.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public String getPlayerNameById(Long playerId) {
        return playerRepository.findById(playerId)
                .map(Player::getFullName)
                .orElse("Unknown Player");
    }

    public String getPlayerTeamById(Long playerId) {
        return playerRepository.findById(playerId)
                .map(Player::getTeamID)
                .map(String::valueOf)
                .orElse("Unknown Team");
    }
}
