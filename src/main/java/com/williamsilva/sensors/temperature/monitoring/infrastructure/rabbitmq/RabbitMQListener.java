package com.williamsilva.sensors.temperature.monitoring.infrastructure.rabbitmq;

import com.williamsilva.sensors.temperature.monitoring.api.model.TemperatureLogData;
import com.williamsilva.sensors.temperature.monitoring.domain.service.SensorAlertService;
import com.williamsilva.sensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.williamsilva.sensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_ALERTING;
import static com.williamsilva.sensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_PROCESS_TEMPERATURE;

@Component
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService;
    private final SensorAlertService sensorAlertService;

    public RabbitMQListener(TemperatureMonitoringService temperatureMonitoringService,
                            SensorAlertService sensorAlertService) {
        this.temperatureMonitoringService = temperatureMonitoringService;
        this.sensorAlertService = sensorAlertService;
    }

    @RabbitListener(queues = QUEUE_PROCESS_TEMPERATURE, concurrency = "2-3")
    public void onMessage(@Payload TemperatureLogData temperatureLogData) {
        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
//        Thread.sleep(Duration.ofSeconds(5));
    }

    @RabbitListener(queues = QUEUE_ALERTING, concurrency = "2-3")
    public void handleAlerting(@Payload TemperatureLogData temperatureLogData) throws InterruptedException {
        sensorAlertService.handleAlert(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }
}
