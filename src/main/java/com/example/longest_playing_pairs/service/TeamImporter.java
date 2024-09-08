package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Team;
import java.util.List;

public interface TeamImporter {
    List<Team> importTeams();
}
