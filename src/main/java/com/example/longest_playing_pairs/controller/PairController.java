package com.example.longest_playing_pairs.controller;

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

    /**
     * Get the pairs of players who played together for the longest time.
     * @return a list of pairs with their playtime details
     */
    @GetMapping("/longest")
    public ResponseEntity<List<Map<String, Object>>> getPairsWithLongestPlaytime() {
        List<Map<String, Object>> pairs = pairService.getPairsWithLongestPlaytime();

        if (pairs != null && !pairs.isEmpty()) {
            return ResponseEntity.ok(pairs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
