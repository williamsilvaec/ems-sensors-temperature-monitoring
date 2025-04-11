package com.williamsilva.sensors.temperature.monitoring.api.controller;

import com.williamsilva.sensors.temperature.monitoring.api.model.SensorMonitoringOuput;
import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorId;
import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.williamsilva.sensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
public class SensorMonitoringController {

    private final SensorMonitoringRepository sensorMonitoringRepository;

    public SensorMonitoringController(SensorMonitoringRepository sensorMonitoringRepository) {
        this.sensorMonitoringRepository = sensorMonitoringRepository;
    }

    @GetMapping
    public SensorMonitoringOuput getDetail(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        return SensorMonitoringOuput.from(sensorMonitoring);
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);

        if (sensorMonitoring.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sensor already enabled");
        }

        sensorMonitoring.setEnabled(true);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable TSID sensorId) throws InterruptedException {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);

        if (sensorMonitoring.isDisabled()) {
            Thread.sleep(Duration.ofSeconds(10));
        }

        sensorMonitoring.setEnabled(false);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

    private SensorMonitoring findByIdOrDefault(TSID sensorId) {
        SensorId id = new SensorId(sensorId);
        return sensorMonitoringRepository.findById(id)
                .orElse(SensorMonitoring.createNew(id));
    }
}
