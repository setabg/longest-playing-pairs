package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Player;
import com.example.longest_playing_pairs.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVPlayerImporter {

    private final PlayerRepository playerRepository;

    @Autowired
    public CSVPlayerImporter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public void importPlayersFromCsv(String filePath) {
        List<Player> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Пропускаме заглавния ред

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Player player = new Player();
                    player.setId(Long.parseLong(data[0].trim())); // Декларация като Long
                    player.setTeamNumber(Integer.parseInt(data[1].trim()));
                    player.setPosition(data[2].trim());
                    player.setFullName(data[3].trim());
                    player.setTeamID(Long.parseLong(data[4].trim()));
                    players.add(player);
                }
            }
            playerRepository.saveAll(players);
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
    }
}
