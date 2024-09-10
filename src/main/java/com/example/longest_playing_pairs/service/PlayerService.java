package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Player;
import com.example.longest_playing_pairs.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id " + id));
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return playerRepository.existsById(id);
    }

    // Method to save a list of players
    public void savePlayers(List<Player> players) {
        playerRepository.saveAll(players);
    }
}
