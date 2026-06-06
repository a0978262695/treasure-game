package com.treasuregame.controller;

import com.treasuregame.dto.LeaderboardEntry;
import com.treasuregame.dto.UserResponse;
import com.treasuregame.repository.UserRepository;
import com.treasuregame.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaderboardController {

    private final GameService gameService;
    private final UserRepository userRepository;

    public LeaderboardController(GameService gameService, UserRepository userRepository) {
        this.gameService = gameService;
        this.userRepository = userRepository;
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard() {
        return ResponseEntity.ok(gameService.getLeaderboard());
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getMe(Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return userRepository.findById(userId)
                .map(u -> ResponseEntity.ok(new UserResponse(
                        u.getId(), u.getEmail(), u.getDisplayName(), u.getTotalScore(), u.getGamesPlayed())))
                .orElse(ResponseEntity.notFound().build());
    }
}
