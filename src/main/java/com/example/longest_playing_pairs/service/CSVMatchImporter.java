package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Match;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVMatchImporter {

    private final MatchService matchService;

    // Define multiple date formats to support different date formats in the CSV
    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
    );

    @Value("src/main/resources/data/matches.csv")
    private String filePath;

    public CSVMatchImporter(MatchService matchService) {
        this.matchService = matchService;
    }

    @Transactional
    public void importMatchesFromCsv() {
        List<Match> matches = readMatchesFromCsv();
        matchService.saveMatches(matches);
    }

    private List<Match> readMatchesFromCsv() {
        List<Match> matches = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header row

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Match match = new Match();
                    match.setId(Long.parseLong(data[0].trim()));
                    match.setaTeamId(Long.parseLong(data[1].trim()));
                    match.setbTeamId(Long.parseLong(data[2].trim()));
                    match.setDate(parseDate(data[3].trim())); // Parse date with multiple formats
                    match.setScore(data[4].trim());

                    matches.add(match);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }

        return matches;
    }

    private LocalDate parseDate(String dateString) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {

            }
        }
        throw new IllegalArgumentException("Date format not supported: " + dateString);
    }
}
