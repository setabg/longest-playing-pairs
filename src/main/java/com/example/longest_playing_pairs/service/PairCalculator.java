package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Record;

import java.util.List;
import java.util.Map;

public interface PairCalculator {
    Map<Long, List<Record>> getPlayersWithOverlappingTimesByMatch();
    List<PlayerPairTime> findLongestPlayingPairsInAllMatches();
}
