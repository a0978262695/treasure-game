package com.treasuregame.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "login_at")
    private LocalDateTime loginAt;

    @Column(name = "logout_at")
    private LocalDateTime logoutAt;

    @PrePersist
    protected void onCreate() {
        loginAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getLoginAt() { return loginAt; }
    public LocalDateTime getLogoutAt() { return logoutAt; }
    public void setLogoutAt(LocalDateTime logoutAt) { this.logoutAt = logoutAt; }
}
