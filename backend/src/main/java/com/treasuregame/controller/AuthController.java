package com.treasuregame.controller;

import com.treasuregame.dto.AuthResponse;
import com.treasuregame.dto.SigninRequest;
import com.treasuregame.dto.SignupRequest;
import com.treasuregame.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@Valid @RequestBody SigninRequest request) {
        return ResponseEntity.ok(authService.signin(request));
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signout(Authentication auth) {
        authService.signout((Long) auth.getPrincipal());
        return ResponseEntity.ok().build();
    }
}
