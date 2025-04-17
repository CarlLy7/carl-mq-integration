package com.carl.mqRabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Component
public class ProducerUtil {
    @Resource
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(String exchange,String routingKey,String jsonStr){
        rabbitTemplate.convertAndSend(exchange,routingKey,jsonStr);
    }
}
