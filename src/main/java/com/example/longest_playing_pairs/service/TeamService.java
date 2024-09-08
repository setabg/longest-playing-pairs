package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Team;
import com.example.longest_playing_pairs.repository.TeamRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamImporter teamImporter;

    public TeamService(TeamRepository teamRepository, TeamImporter teamImporter) {
        this.teamRepository = teamRepository;
        this.teamImporter = teamImporter;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id " + id));
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    @Transactional
    public void importTeamsFromCsv() {
        List<Team> teams = teamImporter.importTeams();
        teamRepository.saveAll(teams);
    }
}
