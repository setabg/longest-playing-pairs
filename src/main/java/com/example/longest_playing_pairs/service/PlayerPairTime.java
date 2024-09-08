package com.example.longest_playing_pairs.service;

public class PlayerPairTime {
    private final Long playerId1;
    private final Long playerId2;
    private final int totalTime;

    public PlayerPairTime(Long playerId1, Long playerId2, int totalTime) {
        this.playerId1 = playerId1;
        this.playerId2 = playerId2;
        this.totalTime = totalTime;
    }

    public Long getPlayerId1() {
        return playerId1;
    }

    public Long getPlayerId2() {
        return playerId2;
    }

    public int getTotalTime() {
        return totalTime;
    }
}

