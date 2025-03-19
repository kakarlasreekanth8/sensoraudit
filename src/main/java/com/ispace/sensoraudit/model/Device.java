package com.ispace.sensoraudit.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "device")
@Builder
public class Device {

    @Id
    @NotBlank(message = "DeviceId is mandatory")
    private String deviceId;

    @NotBlank(message = "RecordType is mandatory")
    private String recordType;

    @NotNull(message = "EventDateTime is mandatory")
    private Instant eventDateTime;

    @NotNull(message = "FieldA is mandatory")
    private Integer fieldA;

    @NotBlank(message = "FieldB is mandatory")
    private String fieldB;

    @NotNull(message = "FieldC is mandatory")
    private Double fieldC;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @CreatedBy
    private String createdBy = "demo user";

    @LastModifiedBy
    private String lastModifiedBy = "demo user";


}
