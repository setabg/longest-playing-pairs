package com.example.longest_playing_pairs.repository;

import com.example.longest_playing_pairs.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
