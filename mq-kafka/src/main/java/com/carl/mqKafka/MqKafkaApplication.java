package com.carl.mqKafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.15
 * @Since: 1.0
 */
@SpringBootApplication
// 开启@KakfaListener注解
@EnableKafka
public class MqKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqKafkaApplication.class, args);
    }
}
