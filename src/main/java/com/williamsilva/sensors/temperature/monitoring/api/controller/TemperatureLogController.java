package com.williamsilva.sensors.temperature.monitoring.api.controller;

import com.williamsilva.sensors.temperature.monitoring.api.model.TemperatureLogData;
import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorId;
import com.williamsilva.sensors.temperature.monitoring.domain.model.TemperatureLog;
import com.williamsilva.sensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import io.hypersistence.tsid.TSID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures")
public class TemperatureLogController {

    private final TemperatureLogRepository temperatureLogRepository;

    public TemperatureLogController(TemperatureLogRepository temperatureLogRepository) {
        this.temperatureLogRepository = temperatureLogRepository;
    }

    @GetMapping
    public Page<TemperatureLogData> search(@PathVariable TSID sensorId, Pageable pageable) {
        Page<TemperatureLog> page = temperatureLogRepository.findAllBySensorId(new SensorId(sensorId), pageable);
        return page.map(TemperatureLogData::from);
    }
}
