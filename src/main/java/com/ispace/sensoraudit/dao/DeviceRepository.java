package com.ispace.sensoraudit.dao;

import com.ispace.sensoraudit.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {


}
