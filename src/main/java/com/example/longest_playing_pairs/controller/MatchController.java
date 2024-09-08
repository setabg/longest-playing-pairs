package com.example.longest_playing_pairs.controller;

import com.example.longest_playing_pairs.entity.Match;
import com.example.longest_playing_pairs.service.CSVMatchImporter;
import com.example.longest_playing_pairs.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private CSVMatchImporter CSVMatchImporter;

    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id);
    }

    @PostMapping
    public Match createMatch(@RequestBody Match match) {
        return matchService.saveMatch(match);
    }

    @PutMapping("/{id}")
    public Match updateMatch(@PathVariable Long id, @RequestBody Match match) {
        match.setId(id);
        return matchService.saveMatch(match);
    }

    @DeleteMapping("/{id}")
    public void deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
    }

    @PostMapping("/import")
    public void importMatches() {
        CSVMatchImporter.importMatchesFromCsv();
    }
}
