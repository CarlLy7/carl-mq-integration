package com.carl.mqRabbitmq.producer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Data
@Component
public class ProducerProperties {
    @Value("${spring.rabbitmq.addresses}")
    private String address;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String passWord;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.connection-timeout}")
    private Integer connectionTimeout;

    @Value("${spring.rabbitmq.publisher-returns}")
    private Boolean publisherReturns;
}
