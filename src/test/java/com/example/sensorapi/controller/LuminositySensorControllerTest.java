package com.example.sensorapi.controller;

import com.example.sensorapi.model.LuminositySensor;
import com.example.sensorapi.service.LuminositySensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LuminositySensorControllerTest {


    private MockMvc mockMvc;

    @Mock
    private LuminositySensorService service;

    @InjectMocks
    private LuminositySensorController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllLuminositySensors() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(new LuminositySensor(), new LuminositySensor()));

        mockMvc.perform(get("/sensors/luminosity")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetSensorByID() throws Exception {
        LuminositySensor sensor = new LuminositySensor();
        sensor.setId("1");
        when(service.findById(anyString())).thenReturn(Optional.of(sensor));

        mockMvc.perform(get("/sensors/luminosity/id")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void testCreateSensor() throws Exception {
        LuminositySensor sensor = new LuminositySensor();
        sensor.setId("1");
        sensor.setType("luminosity");
        when(service.save(any(LuminositySensor.class))).thenReturn(sensor);

        mockMvc.perform(post("/sensors/luminosity")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"uid\": \"123\", \"value\": 25}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.type").value("luminosity"));
    }

    @Test
    public void testUpdateSensor() throws Exception {
        LuminositySensor sensor = new LuminositySensor();
        sensor.setId("1");
        when(service.save(any(LuminositySensor.class))).thenReturn(sensor);

        mockMvc.perform(put("/sensors/luminosity/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"uid\": \"123\", \"value\": 25}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void testDeleteSensor() throws Exception {
        doNothing().when(service).deleteById(anyString());

        mockMvc.perform(delete("/sensors/luminosity/id")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
