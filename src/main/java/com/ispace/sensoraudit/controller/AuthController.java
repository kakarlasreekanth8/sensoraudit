package com.ispace.sensoraudit.controller;

import com.ispace.sensoraudit.constants.ApiConstants;
import com.ispace.sensoraudit.dto.AuthResponse;
import com.ispace.sensoraudit.dto.LoginRequest;
import com.ispace.sensoraudit.service.AuthService;
import com.ispace.sensoraudit.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.AUTH_BASE_URL)
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(ApiConstants.LOGIN_REQUEST)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
