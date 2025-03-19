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

    @NotBlank(message = "Please enter deviceId")
    @JsonProperty("DeviceId")
    private String deviceId;

    @NotBlank(message = "Please enter RecordType")
    @JsonProperty("RecordType")
    private String recordType;

    @NotNull(message = "Please enter EventDateTime")
    @JsonProperty("EventDateTime")
    private Instant eventDateTime;

    @NotNull(message = "Please enter FieldA")
    @JsonProperty("FieldA")
    private Integer fieldA;

    @NotBlank(message = "Please enter FieldB")
    @JsonProperty("FieldB")
    private String fieldB;

    @NotNull(message = "Please enter FieldC")
    @JsonProperty("FieldC")
    private Double fieldC;

}
