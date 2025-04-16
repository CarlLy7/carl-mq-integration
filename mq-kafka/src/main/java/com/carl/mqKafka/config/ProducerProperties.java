package com.carl.mqKafka.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.16
 * @Since: 1.0
 */
@Data
@Component
public class ProducerProperties {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.producer.retries}")
    private int retries;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${spring.kafka.producer.batch-size}")
    private int batchSize;
    @Value("${spring.kafka.producer.buffer-memory}")
    private long bufferMemory;
    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    private long requestTimeoutMs;
    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    private Boolean enableIdempotence;
    @Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection}")
    private int maxInFlightRequestsPerConnection;
    @Value("${spring.kafka.producer.properties.linger.ms}")
    private int lingerMs;

}
