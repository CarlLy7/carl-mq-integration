package com.carl.mqRabbitmq.consumer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Component
@Data
public class ConsumerProperties {
    @Value("${spring.rabbitmq.listener.simple.prefetch}")
    private Integer prefetch;

    @Value("${spring.rabbitmq.listener.simple.batch-size}")
    private Integer batchSize;

    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private Integer concurrency;

    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    private Integer maxConcurrency;

}
