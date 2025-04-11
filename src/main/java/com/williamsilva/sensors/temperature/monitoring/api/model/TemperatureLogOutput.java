package com.williamsilva.sensors.temperature.monitoring.api.model;

import com.williamsilva.sensors.temperature.monitoring.domain.model.TemperatureLog;
import io.hypersistence.tsid.TSID;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TemperatureLogOutput(
        UUID id,
        TSID sensorId,
        OffsetDateTime registeredAt,
        Double value
) {

    public static TemperatureLogOutput from(TemperatureLog temperatureLog) {
        return new TemperatureLogOutput(
                temperatureLog.getId().getValue(),
                temperatureLog.getSensorId().getValue(),
                temperatureLog.getRegisteredAt(),
                temperatureLog.getValue()
        );
    }
}
