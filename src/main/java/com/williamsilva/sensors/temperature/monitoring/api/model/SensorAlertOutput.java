package com.williamsilva.sensors.temperature.monitoring.api.model;

import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorAlert;
import io.hypersistence.tsid.TSID;

public record SensorAlertOutput(TSID id, Double maxTemperature, Double minTemperatute) {

    public static SensorAlertOutput from(SensorAlert alert) {
        return new SensorAlertOutput(
                alert.getId().getValue(),
                alert.getMaxTemperature(),
                alert.getMinTemperature()
        );
    }
}
