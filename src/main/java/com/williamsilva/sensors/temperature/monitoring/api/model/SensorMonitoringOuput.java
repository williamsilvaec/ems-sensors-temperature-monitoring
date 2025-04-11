package com.williamsilva.sensors.temperature.monitoring.api.model;

import com.williamsilva.sensors.temperature.monitoring.domain.model.SensorMonitoring;
import io.hypersistence.tsid.TSID;

import java.time.OffsetDateTime;

public class SensorMonitoringOuput {
    private final TSID id;
    private final Double lastTemperature;
    private final OffsetDateTime updatedAt;
    private final Boolean enabled;

    private SensorMonitoringOuput(TSID value, Double lastTemperature, OffsetDateTime updatedAt, Boolean enabled) {
        this.id = value;
        this.lastTemperature = lastTemperature;
        this.updatedAt = updatedAt;
        this.enabled = enabled;
    }

    public static SensorMonitoringOuput from(SensorMonitoring sensorMonitoring) {
        return new SensorMonitoringOuput(
                sensorMonitoring.getId().getValue(),
                sensorMonitoring.getLastTemperature(),
                sensorMonitoring.getUpdatedAt(),
                sensorMonitoring.getEnabled()
        );
    }

    public TSID getId() {
        return id;
    }

    public Double getLastTemperature() {
        return lastTemperature;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
