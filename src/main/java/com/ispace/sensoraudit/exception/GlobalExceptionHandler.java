package com.ispace.sensoraudit.exception;

import com.ispace.sensoraudit.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = "com.ispace.sensoraudit")
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Bad Request",
                "Invalid request URL: " + ex.getRequestURL()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "Validation error occurred";
        String errorDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((message1, message2) -> message1 + ", " + message2)
                .orElse("Unknown validation error");

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, errorDetails);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = "Constraint violation occurred";
        String errorDetails = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .reduce((message1, message2) -> message1 + ", " + message2)
                .orElse("Unknown constraint violation");

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, errorDetails);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                "The user already exists in the system"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                "Authentication failed, invalid credentials"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
