package com.example.longest_playing_pairs.controller;

import com.example.longest_playing_pairs.entity.Player;
import com.example.longest_playing_pairs.service.CSVPlayerImporter;
import com.example.longest_playing_pairs.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CSVPlayerImporter csvPlayerImporter;

    /**
     * Get all players
     */
    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    /**
     * Get player by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Create a new player
     */
    @PostMapping
    public ResponseEntity<String> createPlayer(@Valid @RequestBody Player player) {
        playerService.savePlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body("Player created successfully!");
    }

    /**
     * Update an existing player by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable Long id, @Valid @RequestBody Player player) {
        if (!playerService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found.");
        }

        player.setId(id);
        playerService.savePlayer(player);
        return ResponseEntity.ok("Player updated successfully!");
    }

    /**
     * Delete a player by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id) {
        if (!playerService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found.");
        }

        playerService.deletePlayer(id);
        return ResponseEntity.ok("Player deleted successfully.");
    }

    /**
     * Import players from CSV file
     */
    @PostMapping("/import")
    public ResponseEntity<String> importPlayers() {
        try {
            csvPlayerImporter.importPlayersFromCsv("src/main/resources/data/players.csv");
            return ResponseEntity.status(HttpStatus.CREATED).body("Players imported successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to import players: " + e.getMessage());
        }
    }
}
