package com.carl.mqKafka.controller;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carl.mqKafka.util.ProducerUtil;

import jakarta.annotation.Resource;

/**
 * @description: 消息生产
 * @author: carl
 * @date: 2025.04.15
 * @Since: 1.0
 */
@RestController
public class TestController {
    @Resource
    private KafkaTemplate<String, Object> myKafkaTemplate;
    @Resource
    private AdminClient adminClient;
    @GetMapping("/test")
    public String test() throws InterruptedException {
        ProducerUtil producerUtil = new ProducerUtil(myKafkaTemplate, adminClient);
        producerUtil.sendMessage("Test333", 2,"zero","zero2");
        return "success";
    }
}
