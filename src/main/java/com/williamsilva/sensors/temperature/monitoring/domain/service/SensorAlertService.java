package com.williamsilva.sensors.temperature.monitoring.domain.service;

import com.williamsilva.sensors.temperature.monitoring.api.model.TemperatureLogData;
import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorId;
import com.williamsilva.sensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorAlertService {

    private static final Logger log = LoggerFactory.getLogger(SensorAlertService.class);

    private final SensorAlertRepository sensorAlertRepository;

    public SensorAlertService(SensorAlertRepository sensorAlertRepository) {
        this.sensorAlertRepository = sensorAlertRepository;
    }

    @Transactional
    public void handleAlert(TemperatureLogData temperatureLogData) {
        sensorAlertRepository.findById(new SensorId(temperatureLogData.sensorId()))
                .ifPresentOrElse(alert -> {

                    if (alert.getMaxTemperature() != null
                            && temperatureLogData.value().compareTo(alert.getMaxTemperature()) >= 0) {
                        log.info("Alert Max Temp: SensorId {} Temp {}",
                                temperatureLogData.sensorId(), temperatureLogData.value());
                    } else if (alert.getMinTemperature() != null
                            && temperatureLogData.value().compareTo(alert.getMinTemperature()) <= 0) {
                        log.info("Alert Min Temp: SensorId {} Temp {}",
                                temperatureLogData.sensorId(), temperatureLogData.value());
                    } else {
                        logIgnoredAlert(temperatureLogData);
                    }

                }, ()-> {
                    logIgnoredAlert(temperatureLogData);
                });
    }

    private static void logIgnoredAlert(TemperatureLogData temperatureLogData) {
        log.info("Alert Ignored: SensorId {} Temp {}",
                temperatureLogData.sensorId(), temperatureLogData.value());
    }
}
