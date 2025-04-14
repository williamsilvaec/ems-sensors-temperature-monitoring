package com.williamsilva.sensors.temperature.monitoring.infrastructure.rabbitmq;

import com.williamsilva.sensors.temperature.monitoring.api.model.TemperatureLogData;
import com.williamsilva.sensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.williamsilva.sensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE;

@Component
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService;

    public RabbitMQListener(TemperatureMonitoringService temperatureMonitoringService) {
        this.temperatureMonitoringService = temperatureMonitoringService;
    }

    @RabbitListener(queues = QUEUE)
    public void onMessage(@Payload TemperatureLogData temperatureLogData) throws InterruptedException {
        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }
}
