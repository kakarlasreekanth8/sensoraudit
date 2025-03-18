package com.ispace.sensoraudit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class DeviceRequest {

    @NotBlank(message = "Please enter deviceID")
    @JsonProperty("DeviceId")
    private String deviceId;

    @NotBlank(message = "Please enter RecordType")
    @JsonProperty("RecordType")
    private String recordType;

    @JsonProperty("EventDateTime")
    private Instant eventDateTime;

    @JsonProperty("FieldA")
    private Integer fieldA;

    @JsonProperty("FieldB")
    private String fieldB;

    @NotNull(message = "Field")
    @JsonProperty("FieldC")
    private Double fieldC;

}
