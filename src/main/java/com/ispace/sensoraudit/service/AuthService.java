package com.ispace.sensoraudit.service;

import com.ispace.sensoraudit.dto.AuthResponse;
import com.ispace.sensoraudit.dto.LoginRequest;

public interface AuthService {
    AuthResponse authenticate(LoginRequest request);
}
