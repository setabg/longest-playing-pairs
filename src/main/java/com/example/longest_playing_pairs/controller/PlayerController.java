package com.example.longest_playing_pairs.controller;

import com.example.longest_playing_pairs.entity.Player;
import com.example.longest_playing_pairs.service.CSVPlayerImporter;
import com.example.longest_playing_pairs.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CSVPlayerImporter CSVPlayerImporter;

    @PostMapping("/import")
    public String importPlayers() {
        try {
            CSVPlayerImporter.importPlayersFromCsv("src/main/resources/data/players.csv");
            return "Players imported successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to import players: " + e.getMessage();
        }
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }
}
