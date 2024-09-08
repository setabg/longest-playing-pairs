package com.example.longest_playing_pairs.controller;

import com.example.longest_playing_pairs.repository.PlayerRepository;
import com.example.longest_playing_pairs.service.PairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pairs")
public class PairController {

    private final PairService pairService;

    @Autowired
    public PairController(PairService pairService) {
        this.pairService = pairService;
    }

    @GetMapping("/longest")
    public ResponseEntity<?> getLongestPlayingPair() {
        List<Map<String, Object>> response = pairService.getLongestPlayingPairsWithNames();

        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No player pair found.");
        }
    }
}
