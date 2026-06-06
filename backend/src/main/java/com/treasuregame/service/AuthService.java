package com.treasuregame.service;

import com.treasuregame.dto.AuthResponse;
import com.treasuregame.dto.SigninRequest;
import com.treasuregame.dto.SignupRequest;
import com.treasuregame.model.Session;
import com.treasuregame.model.User;
import com.treasuregame.repository.SessionRepository;
import com.treasuregame.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository,
                       JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("此 Email 已被使用");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setDisplayName(request.getDisplayName());
        userRepository.save(user);

        createSession(user);
        return new AuthResponse(jwtService.generateToken(user), user.getId(), user.getDisplayName(), user.getEmail());
    }

    public AuthResponse signin(SigninRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email 或密碼錯誤"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Email 或密碼錯誤");
        }
        createSession(user);
        return new AuthResponse(jwtService.generateToken(user), user.getId(), user.getDisplayName(), user.getEmail());
    }

    public void signout(Long userId) {
        sessionRepository.findTopByUser_IdOrderByLoginAtDesc(userId).ifPresent(session -> {
            session.setLogoutAt(LocalDateTime.now());
            sessionRepository.save(session);
        });
    }

    private void createSession(User user) {
        Session session = new Session();
        session.setUser(user);
        sessionRepository.save(session);
    }
}
