package com.example.longest_playing_pairs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Team A ID cannot be null")
    private Long aTeamId;

    @NotNull(message = "Team B ID cannot be null")
    private Long bTeamId;

    @NotNull
    @DateTimeFormat
    private LocalDate date;

    @Size(min = 5, max = 7, message = "Score must be between 5 and 7 characters")
    private String score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getATeamId() {
        return aTeamId;
    }

    public void setATeamId(Long aTeamId) {
        this.aTeamId = aTeamId;
    }

    public Long getBTeamId() {
        return bTeamId;
    }

    public void setBTeamId(Long bTeamId) {
        this.bTeamId = bTeamId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
