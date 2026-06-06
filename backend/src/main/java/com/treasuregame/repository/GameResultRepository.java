package com.treasuregame.repository;

import com.treasuregame.model.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
}
