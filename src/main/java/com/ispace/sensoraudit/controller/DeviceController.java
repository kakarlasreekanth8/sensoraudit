package com.ispace.sensoraudit.controller;

import com.ispace.sensoraudit.dto.DeviceRequest;
import com.ispace.sensoraudit.dto.DeviceResponse;
import com.ispace.sensoraudit.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/nocontent")
    public ResponseEntity<Void> noContent(@Valid @RequestBody DeviceRequest deviceRequest) {

        deviceService.saveDevice(deviceRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/echo")
    public ResponseEntity<?> echo(@RequestBody DeviceRequest deviceRequest) {
        deviceService.saveDevice(deviceRequest);
        return ResponseEntity.ok(deviceRequest);
    }

    @PostMapping("/device")
    public ResponseEntity<String> saveDevice(@RequestBody DeviceRequest device) {
        DeviceResponse deviceResponse = deviceService.saveDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceResponse.getDeviceId());
    }
   @GetMapping("/devicesList")
    public ResponseEntity<List<DeviceResponse>> listOfRequests() {
        List<DeviceResponse> devicesList = deviceService.getDevicesList();
        return ResponseEntity.status(HttpStatus.OK).body(devicesList);
    }


}
