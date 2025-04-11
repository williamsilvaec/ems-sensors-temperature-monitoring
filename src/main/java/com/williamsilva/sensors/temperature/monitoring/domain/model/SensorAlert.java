package com.williamsilva.sensors.temperature.monitoring.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class SensorAlert {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "bigint"))
    private SensorId id;
    private Double maxTemperature;
    private Double minTemperature;

    public SensorAlert() {
    }

    private SensorAlert(SensorId sensorId, Double maxTemperature, Double minTemperature) {
        this.id = sensorId;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public SensorId getId() {
        return id;
    }

    public void setId(SensorId id) {
        this.id = id;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SensorAlert that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static SensorAlert create(SensorId sensorId) {
        return new SensorAlert(sensorId, null, null);
    }
}
