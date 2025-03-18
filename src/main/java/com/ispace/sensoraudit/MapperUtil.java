package com.ispace.sensoraudit;

import com.ispace.sensoraudit.dto.DeviceRequest;
import com.ispace.sensoraudit.dto.DeviceResponse;
import com.ispace.sensoraudit.model.Device;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MapperUtil {

    public static Device deviceFrom(DeviceRequest deviceRequest){
        Device device = new Device();
        BeanUtils.copyProperties(deviceRequest, device);
        return device;
    };

    public static DeviceResponse deviceResponseFrom(Device saveResponse) {
        DeviceResponse deviceResponse = new DeviceResponse();
        BeanUtils.copyProperties(saveResponse, deviceResponse);
        return deviceResponse;
    }


    public static List<DeviceResponse> ListOfDevices(List<Device> devices) {

        return devices.stream().map(MapperUtil::deviceResponseFrom)
                .collect(Collectors.toList());


    }
}
