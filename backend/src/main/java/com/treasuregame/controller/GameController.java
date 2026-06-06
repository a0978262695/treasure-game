package com.treasuregame.controller;

import com.treasuregame.dto.GameResultRequest;
import com.treasuregame.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/result")
    public ResponseEntity<Void> saveResult(@RequestBody GameResultRequest request, Authentication auth) {
        gameService.saveResult((Long) auth.getPrincipal(), request);
        return ResponseEntity.ok().build();
    }
}
