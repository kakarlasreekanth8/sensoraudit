package com.ispace.sensoraudit.service;

import com.ispace.sensoraudit.dto.DeviceRequest;
import com.ispace.sensoraudit.dto.DeviceResponse;

import java.util.List;

public interface DeviceService {

    DeviceResponse saveDevice(DeviceRequest deviceRequest);
    List<DeviceResponse> getDevicesList();
}
