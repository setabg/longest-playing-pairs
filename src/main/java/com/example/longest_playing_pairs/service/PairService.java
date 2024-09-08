package com.example.longest_playing_pairs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PairService {

    private final PairCalculator pairCalculator;
    private final PlayerNameResolver playerNameResolver;

    @Autowired
    public PairService(PairCalculator pairCalculator, PlayerNameResolver playerNameResolver) {
        this.pairCalculator = pairCalculator;
        this.playerNameResolver = playerNameResolver;
    }

    // Method to get the longest playing pairs with their names
    public List<Map<String, Object>> getLongestPlayingPairsWithNames() {
        List<PlayerPairTime> longestPairs = pairCalculator.findLongestPlayingPairsInAllMatches();

        return longestPairs.stream()
                .map(pair -> {
                    String playerName1 = playerNameResolver.getPlayerNameById(pair.getPlayerId1());
                    String playerName2 = playerNameResolver.getPlayerNameById(pair.getPlayerId2());

                    Map<String, Object> map = new HashMap<>();
                    map.put("player 1", playerName1);
                    map.put("player 2", playerName2);
                    map.put("totalTime", pair.getTotalTime());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
