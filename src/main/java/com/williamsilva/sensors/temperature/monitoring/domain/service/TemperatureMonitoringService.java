package com.williamsilva.sensors.temperature.monitoring.domain.service;

import com.williamsilva.sensors.temperature.monitoring.api.model.TemperatureLogData;
import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorId;
import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.williamsilva.sensors.temperature.monitoring.domain.model.TemperatureLog;
import com.williamsilva.sensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.williamsilva.sensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class TemperatureMonitoringService {

    private static final Logger log = LoggerFactory.getLogger(TemperatureMonitoringService.class);

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    public TemperatureMonitoringService(SensorMonitoringRepository sensorMonitoringRepository,
                                        TemperatureLogRepository temperatureLogRepository) {
        this.sensorMonitoringRepository = sensorMonitoringRepository;
        this.temperatureLogRepository = temperatureLogRepository;
    }

    @Transactional
    public void processTemperatureReading(TemperatureLogData temperatureLogData) {
        log.info("processTemperatureReading");
        if (temperatureLogData.value().equals(10.5)) {
            throw new RuntimeException("Test error");
        }
        sensorMonitoringRepository.findById(new SensorId(temperatureLogData.sensorId()))
                .ifPresentOrElse(
                        sensor -> handleSensorMonitoring(temperatureLogData, sensor),
                        () -> logIgnoredTemperature(temperatureLogData)
                );
    }

    private void handleSensorMonitoring(TemperatureLogData temperatureLogData, SensorMonitoring sensor) {
        if (sensor.isEnabled()) {
            sensor.setLastTemperature(temperatureLogData.value());
            sensor.setUpdatedAt(OffsetDateTime.now());
            sensorMonitoringRepository.save(sensor);

            TemperatureLog temperatureLog = TemperatureLog.from(temperatureLogData);
            temperatureLogRepository.save(temperatureLog);
            log.info("Temperature Updated: SensorId {} Temp {}",
                    temperatureLogData.sensorId(), temperatureLogData.value());

        } else {
            logIgnoredTemperature(temperatureLogData);
        }

    }

    private void logIgnoredTemperature(TemperatureLogData temperatureLogData) {
        log.info("Temperature Ignored: SensorId {} Temp {}", temperatureLogData.sensorId(), temperatureLogData.value());
    }
}
