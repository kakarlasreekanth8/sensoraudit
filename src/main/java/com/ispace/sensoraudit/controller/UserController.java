package com.ispace.sensoraudit.controller;

import com.ispace.sensoraudit.constants.ApiConstants;
import com.ispace.sensoraudit.dto.AuthResponse;
import com.ispace.sensoraudit.dto.RegisterRequest;
import com.ispace.sensoraudit.exception.UserAlreadyExistsException;
import com.ispace.sensoraudit.model.User;
import com.ispace.sensoraudit.service.UserService;
import com.ispace.sensoraudit.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.USER_BASE_URL)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping(ApiConstants.REGISTRATION_REQUEST)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {

            User savedUser = userService.registerUser(registerRequest);

            String token = jwtUtils.generateToken(savedUser.getEmail());
            String refreshToken = jwtUtils.generateRefreshToken(savedUser.getEmail());

            return ResponseEntity.ok(AuthResponse.builder()
                    .accessToken(token)
                    .refreshToken(refreshToken)
                    .expiresIn(3600)
                    .build());
    }
}
