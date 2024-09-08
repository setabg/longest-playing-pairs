package com.example.longest_playing_pairs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aTeamId;
    private Long bTeamId;
    private LocalDate date;
    private String score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getaTeamId() {
        return aTeamId;
    }

    public void setaTeamId(Long aTeamId) {
        this.aTeamId = aTeamId;
    }

    public Long getbTeamId() {
        return bTeamId;
    }

    public void setbTeamId(Long bTeamId) {
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
