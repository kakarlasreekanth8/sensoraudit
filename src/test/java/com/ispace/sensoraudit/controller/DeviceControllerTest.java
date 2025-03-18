package com.ispace.sensoraudit.controller;

import com.ispace.sensoraudit.dao.DeviceRepository;
import com.ispace.sensoraudit.model.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void shouldSaveDeviceSuccessfully() throws Exception {
        String payload = """
        {
            "RecordType": "10006",
            "DeviceId": "357370040159772",
            "EventDateTime": "2014-05-12T05:09:48Z",
            "FieldA": 68,
            "FieldB": "xxx",
            "FieldC": 123.45
        }""";

        mockMvc.perform(post("/api/device")
                        .header("Authorization", "Bearer aaaa-bbbb")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());
    }


    @Test
    void shouldReturnEchoPayloadForEchoEndpoint() throws Exception {
        String payload = """
        {
            "RecordType": "10006",
            "DeviceId": "357370040159772"
        }""";

        mockMvc.perform(post("/api/echo")
                        .header("Authorization", "Bearer aaaa-bbbb")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().json(payload));
    }

}