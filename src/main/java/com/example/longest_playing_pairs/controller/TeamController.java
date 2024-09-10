package com.example.longest_playing_pairs.controller;

import com.example.longest_playing_pairs.entity.Team;
import com.example.longest_playing_pairs.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @PostMapping
    public Team createTeam(@Valid @RequestBody Team team) {
        return teamService.saveTeam(team);
    }

    @PutMapping("/{id}")
    public Team updateTeam(@PathVariable Long id, @Valid @RequestBody Team team) {
        team.setId(id);
        return teamService.saveTeam(team);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }

    @PostMapping("/import")
    public void importTeamsFromCsv() {
        teamService.importTeamsFromCsv();
    }
}
