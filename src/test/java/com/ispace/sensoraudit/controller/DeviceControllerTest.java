package com.ispace.sensoraudit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ispace.sensoraudit.constants.ApiConstants;
import com.ispace.sensoraudit.dto.DeviceRequest;
import com.ispace.sensoraudit.dto.DeviceResponse;
import com.ispace.sensoraudit.dto.UserResponse;
import com.ispace.sensoraudit.service.DeviceService;
import com.ispace.sensoraudit.service.UserService;
import com.ispace.sensoraudit.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest {

    @MockitoBean
    private DeviceService deviceService;

    @MockitoBean
    private UserService userService;

    @InjectMocks
    private DeviceController deviceController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    private ObjectMapper objectMapper;
    private DeviceRequest validDeviceRequest;
    private String validToken;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); //to support instance

        validDeviceRequest = DeviceRequest.builder()
                .deviceId("Device123")
                .recordType("TypeA")
                .eventDateTime(Instant.now())
                .fieldA(1)
                .fieldB("FieldB")
                .fieldC(10.5)
                .build();

        when(userService.loadUserByUsername("testuser@example.com"))
                .thenReturn(UserResponse.builder()
                        .userName("testuser@example.com")
                        .password("testpass")
                        .build());

        validToken = jwtUtils.generateToken("testuser@example.com");
    }

    @Test
    void testNoContent_ValidRequest() throws Exception {
        mockMvc.perform(post(ApiConstants.DEVICE_BASE_URL + ApiConstants.POST_NOCONTENT)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDeviceRequest))
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isNoContent());

        verify(deviceService, times(1)).saveDevice(validDeviceRequest);
    }

    @Test
    void testNoContent_InvalidRequest() throws Exception {
        DeviceRequest invalidDeviceRequest = DeviceRequest.builder().build(); // Empty request

        mockMvc.perform(post(ApiConstants.DEVICE_BASE_URL + ApiConstants.POST_NOCONTENT)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidDeviceRequest))
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isBadRequest());

        verify(deviceService, never()).saveDevice(any());
    }

    @Test
    void testNoContent_UnauthorizedRequest() throws Exception {
        mockMvc.perform(post(ApiConstants.DEVICE_BASE_URL + ApiConstants.POST_NOCONTENT)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDeviceRequest)))
                .andExpect(status().isForbidden());

        verify(deviceService, never()).saveDevice(any());
    }

    @Test
    void testSaveDevice_ValidRequest() throws Exception {

        when(deviceService.saveDevice(any(DeviceRequest.class))).thenReturn(getDeviceResponse());
        mockMvc.perform(post(ApiConstants.DEVICE_BASE_URL + ApiConstants.SAVE_DEVICE_DETAILS)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDeviceRequest))
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isCreated())
                .andExpect(content().string(getDeviceResponse().getDeviceId()));

        verify(deviceService, times(1)).saveDevice(validDeviceRequest);
    }

    DeviceResponse getDeviceResponse() {
        DeviceResponse mockedResponse = new DeviceResponse();
        mockedResponse.setDeviceId("D123");
        mockedResponse.setRecordType("TypeA");
        mockedResponse.setEventDateTime(Instant.now());
        mockedResponse.setFieldA(1);
        mockedResponse.setFieldB("FieldB");
        mockedResponse.setFieldC(10.5);
        return mockedResponse;
    }

}
