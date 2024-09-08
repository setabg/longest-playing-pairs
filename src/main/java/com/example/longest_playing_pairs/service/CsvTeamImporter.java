package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Team;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvTeamImporter implements TeamImporter {

    private final String filePath = "src/main/resources/data/teams.csv";

    @Override
    public List<Team> importTeams() {
        List<Team> teams = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    Team team = new Team();
                    team.setId(Long.parseLong(data[0].trim()));
                    team.setName(data[1].trim());
                    team.setManagerFullName(data[2].trim());
                    team.setGroupName(data[3].trim());
                    teams.add(team);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse number from CSV file: " + e.getMessage());
        }

        return teams;
    }
}
