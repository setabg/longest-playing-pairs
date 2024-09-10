package com.example.longest_playing_pairs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Team number cannot be null")
    @Min(value = 1, message = "Team number must be at least 1")
    @Max(value = 99, message = "Team number must be at most 99")
    private Integer teamNumber;

    @NotNull(message = "Position cannot be null")
    @Size(min = 2, max = 3, message = "Position must be between 2 and 3 characters long")
    private String position;

    @NotNull(message = "Full name cannot be null")
    @Size(min = 1, max = 100, message = "Full name must be between 1 and 100 characters long")
    private String fullName;

    @NotNull(message = "Team ID cannot be null")
    private Long teamID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Integer teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getTeamID() {
        return teamID;
    }

    public void setTeamID(Long teamID) {
        this.teamID = teamID;
    }
}
