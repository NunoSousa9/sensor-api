package com.example.sensorapi.controller;

import com.example.sensorapi.model.SensorData;
import com.example.sensorapi.model.TemperatureSensor;
import com.example.sensorapi.service.TemperatureSensorService;
import com.example.sensorapi.service.UIDSequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sensors/temperature")
public class TemperatureSensorController {

    @Autowired
    private TemperatureSensorService temperatureSensorService;

    @Autowired
    private UIDSequenceGeneratorService uidSequenceGeneratorService;

    @GetMapping
    public List<TemperatureSensor> getAll() {
        return temperatureSensorService.findAll();

    }

    @GetMapping("/{id}")
    public Optional<TemperatureSensor> getSensorById(@PathVariable String id) {
        return temperatureSensorService.findById(id);
    }

    @PostMapping
    public TemperatureSensor createSensor(@RequestBody @Valid TemperatureSensor sensorData) {
        sensorData.setType("temperature");
        sensorData.setUid(uidSequenceGeneratorService.generateSequence(SensorData.SEQUENCE_NAME));
        return temperatureSensorService.save(sensorData);

    }

    @PutMapping("/{id}")
    public TemperatureSensor updateSensor(@PathVariable String id, @RequestBody TemperatureSensor sensorDetails) {
        sensorDetails.setId(id);
        return temperatureSensorService.save(sensorDetails);

    }

    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable String id) {
        temperatureSensorService.deleteById(id);
    }
}
