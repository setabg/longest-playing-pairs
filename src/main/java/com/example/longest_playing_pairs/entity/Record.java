package com.example.longest_playing_pairs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Player ID cannot be null")
    private Long playerId;

    @NotNull(message = "Match ID cannot be null")
    private Long matchId;

    @Positive(message = "From minutes must be greater than 0")
    private Integer fromMinutes;

    @Min(value = 0, message = "To minutes must be zero or positive")
    @Max(value = 90, message = "To minutes must be less than or equal to 90")
    private Integer toMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getFromMinutes() {
        return fromMinutes;
    }

    public void setFromMinutes(Integer fromMinutes) {
        this.fromMinutes = fromMinutes;
    }

    public Integer getToMinutes() {
        return toMinutes;
    }

    public void setToMinutes(Integer toMinutes) {
        this.toMinutes = toMinutes;
    }

    public Integer getMinutesPlayed() {
        if (fromMinutes != null && toMinutes != null) {
            return toMinutes - fromMinutes;
        }
        return 0;
    }
}
