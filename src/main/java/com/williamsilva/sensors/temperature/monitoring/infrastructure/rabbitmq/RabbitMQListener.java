package com.williamsilva.sensors.temperature.monitoring.infrastructure.rabbitmq;

import com.williamsilva.sensors.temperature.monitoring.api.model.TemperatureLogData;
import io.hypersistence.tsid.TSID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

import static com.williamsilva.sensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE;

@Component
public class RabbitMQListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

    @RabbitListener(queues = QUEUE)
    public void onMessage(@Payload TemperatureLogData temperatureLogData,
                          @Headers Map<String, Object> headers) throws InterruptedException {
        TSID sensorId = temperatureLogData.sensorId();
        Double temperature = temperatureLogData.value();
        log.info("Temperature updated: SensorId {} Temp {}", sensorId, temperature);
        log.info("Headers: {}", headers);

        Thread.sleep(Duration.ofSeconds(5));
    }
}
