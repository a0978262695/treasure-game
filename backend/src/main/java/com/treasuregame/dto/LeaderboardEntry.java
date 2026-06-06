package com.treasuregame.dto;

public class LeaderboardEntry {

    private Long userId;
    private String displayName;
    private int totalScore;
    private int gamesPlayed;

    public LeaderboardEntry(Long userId, String displayName, int totalScore, int gamesPlayed) {
        this.userId = userId;
        this.displayName = displayName;
        this.totalScore = totalScore;
        this.gamesPlayed = gamesPlayed;
    }

    public Long getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public int getTotalScore() { return totalScore; }
    public int getGamesPlayed() { return gamesPlayed; }
}
