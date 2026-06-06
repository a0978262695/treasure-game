package com.treasuregame.service;

import com.treasuregame.dto.GameResultRequest;
import com.treasuregame.dto.LeaderboardEntry;
import com.treasuregame.model.GameResult;
import com.treasuregame.model.User;
import com.treasuregame.repository.GameResultRepository;
import com.treasuregame.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final UserRepository userRepository;
    private final GameResultRepository gameResultRepository;

    public GameService(UserRepository userRepository, GameResultRepository gameResultRepository) {
        this.userRepository = userRepository;
        this.gameResultRepository = gameResultRepository;
    }

    @Transactional
    public void saveResult(Long userId, GameResultRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        GameResult result = new GameResult();
        result.setUser(user);
        result.setFinalScore(request.getFinalScore());
        result.setFoundTreasure(request.isFoundTreasure());
        result.setBoxesOpened(request.getBoxesOpened());
        gameResultRepository.save(result);

        user.setTotalScore(user.getTotalScore() + request.getFinalScore());
        user.setGamesPlayed(user.getGamesPlayed() + 1);
        userRepository.save(user);
    }

    public List<LeaderboardEntry> getLeaderboard() {
        return userRepository.findTop10ByOrderByTotalScoreDesc()
                .stream()
                .map(u -> new LeaderboardEntry(u.getId(), u.getDisplayName(), u.getTotalScore(), u.getGamesPlayed()))
                .collect(Collectors.toList());
    }
}
