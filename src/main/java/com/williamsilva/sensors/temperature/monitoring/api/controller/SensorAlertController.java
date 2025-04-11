package com.williamsilva.sensors.temperature.monitoring.api.controller;

import com.williamsilva.sensors.temperature.monitoring.api.model.SensorAlertInput;
import com.williamsilva.sensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorAlert;
import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorId;
import com.williamsilva.sensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
public class SensorAlertController {

    private final SensorAlertRepository sensorAlertRepository;

    public SensorAlertController(SensorAlertRepository sensorAlertRepository) {
        this.sensorAlertRepository = sensorAlertRepository;
    }

    @GetMapping
    public SensorAlertOutput getOne(@PathVariable TSID sensorId) {
        SensorAlert sensorAlert = sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return SensorAlertOutput.from(sensorAlert);
    }

    @PutMapping
    public SensorAlertOutput createOrUpdate(@PathVariable TSID sensorId, @RequestBody SensorAlertInput input) {
        Optional<SensorAlert> sensorAlertOptional = sensorAlertRepository.findById(new SensorId(sensorId));

        if (sensorAlertOptional.isEmpty()) {
            SensorAlert sensorAlert = SensorAlert.create(new SensorId(sensorId));
            sensorAlert = sensorAlertRepository.save(sensorAlert);
            return SensorAlertOutput.from(sensorAlert);
        }

        BeanUtils.copyProperties(input, sensorAlertOptional.get(), "id");
        SensorAlert sensorAlert = sensorAlertRepository.save(sensorAlertOptional.get());
        return SensorAlertOutput.from(sensorAlert);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable TSID sensorId) {
        SensorId id = new SensorId(sensorId);

        if (!sensorAlertRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        sensorAlertRepository.deleteById(id);
    }
}
