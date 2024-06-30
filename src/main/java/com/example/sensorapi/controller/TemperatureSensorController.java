package com.example.sensorapi.controller;

import com.example.sensorapi.model.TemperatureSensor;
import com.example.sensorapi.security.TokenUtil;
import com.example.sensorapi.service.TemperatureSensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sensors/temperature")
public class TemperatureSensorController {

    private final TemperatureSensorService temperatureSensorService;

    public TemperatureSensorController(TemperatureSensorService temperatureSensorService) {
        this.temperatureSensorService = temperatureSensorService;
    }

    @GetMapping
    public ResponseEntity<List<TemperatureSensor>> getAll(HttpServletRequest request) {
        TokenUtil.validateToken(request);
        List<TemperatureSensor> sensors = temperatureSensorService.findAll();
        return ResponseEntity.ok(sensors);
    }

    @GetMapping("/id")
    public ResponseEntity<TemperatureSensor> getSensorById(@PathVariable String id, HttpServletRequest request) {
        TokenUtil.validateToken(request);
        return temperatureSensorService.findById(id)
                .map(sensor -> ResponseEntity.ok().body(sensor))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TemperatureSensor> createSensor(@RequestBody TemperatureSensor sensorData, HttpServletRequest request) {
        TokenUtil.validateToken(request);
        TemperatureSensor savedSensor = temperatureSensorService.save(sensorData);
        return ResponseEntity.ok(savedSensor);
    }

    @PutMapping("/id")
    public ResponseEntity<TemperatureSensor> updateSensor(@PathVariable String id, @RequestBody TemperatureSensor sensorDetails, HttpServletRequest request) {
        TokenUtil.validateToken(request);
        sensorDetails.setId(id);
        TemperatureSensor updatedSensor = temperatureSensorService.save(sensorDetails);
        return ResponseEntity.ok(updatedSensor);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteSensor(@PathVariable String id, HttpServletRequest request) {
        TokenUtil.validateToken(request);
        temperatureSensorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
