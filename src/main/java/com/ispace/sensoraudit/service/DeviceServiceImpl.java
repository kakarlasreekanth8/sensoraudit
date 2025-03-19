package com.ispace.sensoraudit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ispace.sensoraudit.MapperUtil;
import com.ispace.sensoraudit.adaptor.InstantTypeAdapter;
import com.ispace.sensoraudit.repository.AuditLogRepository;
import com.ispace.sensoraudit.repository.DeviceRepository;
import com.ispace.sensoraudit.dto.DeviceRequest;
import com.ispace.sensoraudit.dto.DeviceResponse;
import com.ispace.sensoraudit.model.AuditLog;
import com.ispace.sensoraudit.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    AuditLogRepository auditLogRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public DeviceResponse saveDevice(DeviceRequest deviceRequest) {

        Device device = MapperUtil.deviceFrom(deviceRequest);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class,
                        new InstantTypeAdapter())
                .create();
        String deviceAudit = gson.toJson(device);

        Device saveResponse = deviceRepository.save(device);
        auditLogRepository.save(AuditLog.builder().data(deviceAudit).build());

        return MapperUtil.deviceResponseFrom(saveResponse);

    }

    @Override
    public List<DeviceResponse> getDevicesList() {
        List<Device> devices = deviceRepository.findAll();
        return MapperUtil.ListOfDevices(devices);

    }

}
