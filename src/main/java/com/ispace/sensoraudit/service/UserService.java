package com.ispace.sensoraudit.service;

import com.ispace.sensoraudit.dto.RegisterRequest;
import com.ispace.sensoraudit.dto.UserResponse;
import com.ispace.sensoraudit.model.User;

public interface UserService {
    public User registerUser(RegisterRequest registerRequest);

    public UserResponse loadUserByUsername(String userName);
}
