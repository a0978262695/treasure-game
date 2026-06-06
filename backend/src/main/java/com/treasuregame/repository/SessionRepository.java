package com.treasuregame.repository;

import com.treasuregame.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findTopByUser_IdOrderByLoginAtDesc(Long userId);
}
