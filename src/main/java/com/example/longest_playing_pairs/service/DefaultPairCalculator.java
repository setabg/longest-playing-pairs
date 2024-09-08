package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Record;
import com.example.longest_playing_pairs.repository.PlayerRepository;
import com.example.longest_playing_pairs.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultPairCalculator implements PairCalculator {

    private final RecordRepository recordRepository;
    private final PlayerRepository playerRepository;
    private final PlayerTeamResolver playerTeamResolver;

    @Autowired
    public DefaultPairCalculator(RecordRepository recordRepository,
                                 PlayerRepository playerRepository,
                                 PlayerTeamResolver playerTeamResolver) {
        this.recordRepository = recordRepository;
        this.playerRepository = playerRepository;
        this.playerTeamResolver = playerTeamResolver;
    }

    @Override
    public Map<Long, List<Record>> getPlayersWithOverlappingTimesByMatch() {
        List<Record> allRecords = recordRepository.findAll();
        return allRecords.stream()
                .collect(Collectors.groupingBy(Record::getMatchId));
    }

    @Override
    public List<PlayerPairTime> findLongestPlayingPairsInAllMatches() {
        Map<Long, List<Record>> recordsByMatch = getPlayersWithOverlappingTimesByMatch();
        List<PlayerPairTime> allPairs = new ArrayList<>();

        for (List<Record> records : recordsByMatch.values()) {
            Map<Pair<Long, Long>, Integer> pairPlaytimeMap = new HashMap<>();

            for (int i = 0; i < records.size(); i++) {
                Record record1 = records.get(i);
                for (int j = i + 1; j < records.size(); j++) {
                    Record record2 = records.get(j);
                    if (record1.getPlayerId().equals(record2.getPlayerId())) continue;

                    int overlapStart = Math.max(record1.getFromMinutes(), record2.getFromMinutes());
                    int overlapEnd = Math.min(record1.getToMinutes(), record2.getToMinutes());

                    if (overlapStart < overlapEnd) {
                        int overlapMinutes = overlapEnd - overlapStart;
                        Pair<Long, Long> playerPair = new Pair<>(
                                Math.min(record1.getPlayerId(), record2.getPlayerId()),
                                Math.max(record1.getPlayerId(), record2.getPlayerId())
                        );
                        pairPlaytimeMap.merge(playerPair, overlapMinutes, Integer::sum);
                    }
                }
            }

            List<PlayerPairTime> longestPairs = pairPlaytimeMap.entrySet().stream()
                    .map(entry -> {
                        Long playerId1 = entry.getKey().getFirst();
                        Long playerId2 = entry.getKey().getSecond();
                        String playerTeam1 = playerTeamResolver.getPlayerTeamById(playerId1);
                        String playerTeam2 = playerTeamResolver.getPlayerTeamById(playerId2);

                        if (playerTeam1.equals(playerTeam2)) {
                            return new PlayerPairTime(playerId1, playerId2, entry.getValue());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            allPairs.addAll(longestPairs);
        }

        int maxPlaytime = allPairs.stream()
                .mapToInt(PlayerPairTime::getTotalTime)
                .max()
                .orElse(0);

        return allPairs.stream()
                .filter(pair -> pair.getTotalTime() == maxPlaytime)
                .collect(Collectors.toList());
    }

    private static class Pair<T, U> {
        private final T first;
        private final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }
}
