package com.ispace.sensoraudit.dto;

public class ErrorResponse {
    private String errorMessage;
    private String errorDetails;

    public ErrorResponse(String errorMessage, String errorDetails) {
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    // Getters and setters
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
