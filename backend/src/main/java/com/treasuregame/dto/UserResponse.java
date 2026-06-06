package com.treasuregame.dto;

public class UserResponse {

    private Long id;
    private String email;
    private String displayName;
    private int totalScore;
    private int gamesPlayed;

    public UserResponse(Long id, String email, String displayName, int totalScore, int gamesPlayed) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.totalScore = totalScore;
        this.gamesPlayed = gamesPlayed;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getDisplayName() { return displayName; }
    public int getTotalScore() { return totalScore; }
    public int getGamesPlayed() { return gamesPlayed; }
}
