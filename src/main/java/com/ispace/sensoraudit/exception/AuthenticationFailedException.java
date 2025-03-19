package com.ispace.sensoraudit.exception;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException(String message) {
        super("Invalid email or password.");
    }
}
