package com.ispace.sensoraudit.service;

import com.ispace.sensoraudit.dto.AuthResponse;
import com.ispace.sensoraudit.dto.LoginRequest;
import com.ispace.sensoraudit.exception.AuthenticationFailedException;
import com.ispace.sensoraudit.model.User;
import com.ispace.sensoraudit.repository.UserRepository;
import com.ispace.sensoraudit.utils.JwtUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse authenticate(LoginRequest request) {

            userRepository.findByEmail(request.getEmail())
                    .filter(user -> request.getPassword() != null && user.getPassword() != null
                            && passwordEncoder.matches(request.getPassword(), user.getPassword()))
                    .orElseThrow(() -> new AuthenticationFailedException("Invalid email or password"));

        String token = jwtUtils.generateToken(request.getEmail());

        return AuthResponse.builder()
                .accessToken(token)
                .expiresIn( Math.toIntExact(JwtUtils.EXPIRATION_TIME))
                .build();
    }
}
