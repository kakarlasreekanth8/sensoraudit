package com.ispace.sensoraudit.controller;

import com.ispace.sensoraudit.constants.ApiConstants;
import com.ispace.sensoraudit.dto.DeviceRequest;
import com.ispace.sensoraudit.dto.DeviceResponse;
import com.ispace.sensoraudit.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.DEVICE_BASE_URL)
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(ApiConstants.POST_NOCONTENT)
    public ResponseEntity<Void> noContent(@Valid @RequestBody DeviceRequest deviceRequest) {

        deviceService.saveDevice(deviceRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(ApiConstants.POST_ECHO)
    public ResponseEntity<DeviceResponse> echo( @Valid @RequestBody DeviceRequest deviceRequest) {
        DeviceResponse deviceResponse = deviceService.saveDevice(deviceRequest);
        return ResponseEntity.ok(deviceResponse);
    }

    @PostMapping(ApiConstants.SAVE_DEVICE_DETAILS)
    public ResponseEntity<String> saveDevice(@Valid @RequestBody DeviceRequest device) {
        DeviceResponse deviceResponse = deviceService.saveDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceResponse.getDeviceId());
    }
   @GetMapping(ApiConstants.GET_DEVICES_LIST)
    public ResponseEntity<List<DeviceResponse>> listOfRequests() {
        List<DeviceResponse> devicesList = deviceService.getDevicesList();
        return ResponseEntity.status(HttpStatus.OK).body(devicesList);
    }

}
