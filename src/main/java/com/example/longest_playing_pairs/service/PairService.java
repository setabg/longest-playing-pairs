package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Record;
import com.example.longest_playing_pairs.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PairService {

    @Autowired
    private RecordRepository recordRepository;

    /**
     * Method to get a map with MatchID as the key and a list of records (with overlapping times) as the value
     *
     * @return A map with MatchID as the key and a list of records as the value
     */
    public Map<Long, List<Map<String, Object>>> getPlayersWithOverlappingTimesByMatch() {
        List<Record> allRecords = recordRepository.findAll();
        Set<Long> matchIds = allRecords.stream()
                .map(Record::getMatchId)
                .collect(Collectors.toSet());
        Map<Long, List<Map<String, Object>>> recordsByMatch = new HashMap<>();

        for (Long matchId : matchIds) {
            List<Object[]> records = recordRepository.findPlayerPairsWithLongestPlaytime(matchId);

            if (records.isEmpty()) {
                continue;
            }

            // Find the maximum overlapping minutes
            int maxOverlap = records.stream()
                    .mapToInt(record -> (int) record[2])
                    .max()
                    .orElse(0);

            // Normalize player names and filter out duplicate pairs
            Set<String> seenPairs = new HashSet<>();
            List<Map<String, Object>> longestPairs = records.stream()
                    .filter(record -> (int) record[2] == maxOverlap)
                    .map(record -> {
                        String player1Name = (String) record[0];
                        String player2Name = (String) record[1];
                        String sortedPair = player1Name.compareTo(player2Name) < 0
                                ? player1Name + "-" + player2Name
                                : player2Name + "-" + player1Name;

                        // Only add the pair if it hasn't been seen
                        if (seenPairs.add(sortedPair)) {
                            return Map.of(
                                    "player1Name", player1Name,
                                    "player2Name", player2Name,
                                    "overlappingMinutes", record[2]
                            );
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            recordsByMatch.put(matchId, longestPairs);
        }

        return recordsByMatch;
    }

    /**
     * Method to get pairs with the longest playtime across all matches
     *
     * @return A list of pairs with the longest playtime
     */
    public List<Map<String, Object>> getPairsWithLongestPlaytime() {
        // Map to store all results by matchId
        Map<Long, List<Map<String, Object>>> longestPairsByMatch = getPlayersWithOverlappingTimesByMatch();

        // Find the maximum overlapping minutes across all matches
        int maxOverlap = longestPairsByMatch.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(record -> (int) record.get("overlappingMinutes"))
                .max()
                .orElse(0);

        // Filter all records to include only those with the maximum overlapping minutes
        List<Map<String, Object>> longestPairs = longestPairsByMatch.values().stream()
                .flatMap(Collection::stream)
                .filter(record -> (int) record.get("overlappingMinutes") == maxOverlap)
                .collect(Collectors.toList());

        return longestPairs;
    }
}
