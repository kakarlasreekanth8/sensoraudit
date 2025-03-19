package com.ispace.sensoraudit.service;

import com.ispace.sensoraudit.dto.RegisterRequest;
import com.ispace.sensoraudit.dto.UserResponse;
import com.ispace.sensoraudit.exception.UserAlreadyExistsException;
import com.ispace.sensoraudit.model.User;
import com.ispace.sensoraudit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;



   // @Transactional
    public User registerUser(RegisterRequest registerRequest) {
        userRepository.existsByEmail(registerRequest.getEmail())
                .ifPresent(exists -> {
                            if (exists) {
                                throw new UserAlreadyExistsException("User already has an account");
                            }
                });

        User newUser = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public UserResponse loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .map(user -> UserResponse.builder()
                        .userName(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
