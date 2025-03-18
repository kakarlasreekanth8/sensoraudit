package com.ispace.sensoraudit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class DeviceResponse {

    private String deviceId;
    private String recordType;
    private Instant eventDateTime;
    private Integer fieldA;
    private String fieldB;
    private Double fieldC;

}
