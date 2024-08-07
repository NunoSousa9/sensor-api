package com.example.sensorapi.integration;

import com.example.sensorapi.model.TemperatureSensor;
import com.example.sensorapi.repository.TemperatureSensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class TemperatureSensorControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TemperatureSensorRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreateAndGetSensor() throws Exception {
        String sensorJson = "{\"type\":\"temperature\", \"value\": 25.5}";

        MvcResult result = mockMvc.perform(post("/sensors/temperature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sensorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(25.5))
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Integer uidInt = JsonPath.read(responseJson, "$.uid");
        Long uid = uidInt.longValue(); // Convert to Long if necessary
        String id = JsonPath.read(responseJson, "$.id");

        TemperatureSensor sensor = repository.findById(id).orElse(null);
        assertThat(sensor).isNotNull();
        assertThat(sensor.getUid()).isEqualTo(uid);
        assertThat(sensor.getValue()).isEqualTo(25.5);

        mockMvc.perform(get("/sensors/temperature/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value(uid))
                .andExpect(jsonPath("$.value").value(25.5));
    }
}
