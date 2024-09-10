package com.example.longest_playing_pairs.repository;

import com.example.longest_playing_pairs.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "SELECT p1.full_name AS player1_name, " +
            "p2.full_name AS player2_name, " +
            "GREATEST(0, LEAST(r1.to_minutes, r2.to_minutes) - GREATEST(r1.from_minutes, r2.from_minutes)) AS overlapping_minutes " +
            "FROM Record r1 " +
            "JOIN Record r2 ON r1.match_id = r2.match_id " +
            "AND r1.player_id <> r2.player_id " +
            "AND (r1.from_minutes < r2.to_minutes AND r1.to_minutes > r2.from_minutes) " +
            "JOIN Player p1 ON r1.player_id = p1.id " +
            "JOIN Player p2 ON r2.player_id = p2.id " +
            "WHERE r1.match_id = :matchId " +
            "AND p1.teamid = p2.teamid ",
            nativeQuery = true)
    List<Object[]> findPlayerPairsWithLongestPlaytime(@Param("matchId") Long matchId);


    List<Record> findByMatchId(Long matchId);

    Optional<Record> findByPlayerIdAndMatchId(Long playerId, Long matchId);
}
