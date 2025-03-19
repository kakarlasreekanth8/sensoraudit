package com.ispace.sensoraudit.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {

    private String userName;
    private String password;


}
