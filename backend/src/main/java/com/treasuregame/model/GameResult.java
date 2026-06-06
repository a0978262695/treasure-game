package com.treasuregame.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_results")
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "final_score", nullable = false)
    private int finalScore;

    @Column(name = "found_treasure", nullable = false)
    private boolean foundTreasure;

    @Column(name = "boxes_opened", nullable = false)
    private int boxesOpened;

    @Column(name = "played_at")
    private LocalDateTime playedAt;

    @PrePersist
    protected void onCreate() {
        playedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public int getFinalScore() { return finalScore; }
    public void setFinalScore(int finalScore) { this.finalScore = finalScore; }
    public boolean isFoundTreasure() { return foundTreasure; }
    public void setFoundTreasure(boolean foundTreasure) { this.foundTreasure = foundTreasure; }
    public int getBoxesOpened() { return boxesOpened; }
    public void setBoxesOpened(int boxesOpened) { this.boxesOpened = boxesOpened; }
    public LocalDateTime getPlayedAt() { return playedAt; }
}
