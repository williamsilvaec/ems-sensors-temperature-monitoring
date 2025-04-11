package com.williamsilva.sensors.temperature.monitoring.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class TemperatureLogId implements Serializable {

    private UUID value;

    public TemperatureLogId() {
    }

    public TemperatureLogId(UUID value) {
        this.value = value;
    }
    public TemperatureLogId(String value) {
        this.value = UUID.fromString(value);
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
