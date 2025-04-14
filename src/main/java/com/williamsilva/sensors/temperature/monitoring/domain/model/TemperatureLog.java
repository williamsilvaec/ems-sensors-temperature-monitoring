package com.williamsilva.sensors.temperature.monitoring.domain.model;

import com.williamsilva.sensors.temperature.monitoring.api.model.TemperatureLogData;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class TemperatureLog {

    @Id
    @AttributeOverride(name = "value",
            column = @Column(name = "id", columnDefinition = "uuid"))
    private TemperatureLogId id;

    @Column(name = "\"value\"")
    private Double value;

    private OffsetDateTime registeredAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sensor_id", columnDefinition = "bigint"))
    private SensorId sensorId;

    public TemperatureLog() {
    }

    private TemperatureLog(TemperatureLogId id, Double value, OffsetDateTime registeredAt, SensorId sensorId) {
        this.id = id;
        this.value = value;
        this.registeredAt = registeredAt;
        this.sensorId = sensorId;
    }

    public TemperatureLogId getId() {
        return id;
    }

    public void setId(TemperatureLogId id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public OffsetDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(OffsetDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public SensorId getSensorId() {
        return sensorId;
    }

    public void setSensorId(SensorId sensorId) {
        this.sensorId = sensorId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TemperatureLog that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static TemperatureLog from(TemperatureLogData data) {
        return new TemperatureLog(
                new TemperatureLogId(data.id()),
                data.value(),
                data.registeredAt(),
                new SensorId(data.sensorId())
        );
    }
}
