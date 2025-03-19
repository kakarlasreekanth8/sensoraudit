package com.ispace.sensoraudit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    @Builder.Default
    private String tokenType = "Bearer";

    private String accessToken;

    private int expiresIn;

    private String refreshToken;
}
