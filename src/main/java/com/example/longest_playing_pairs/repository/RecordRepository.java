package com.example.longest_playing_pairs.repository;

import com.example.longest_playing_pairs.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByMatchId(Long matchId);

    Optional<Record> findByPlayerIdAndMatchId(Long playerId, Long matchId);
}
