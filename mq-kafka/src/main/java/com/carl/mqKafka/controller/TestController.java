package com.carl.mqKafka.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carl.mqKafka.util.ProducerUtil;

import jakarta.annotation.Resource;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.15
 * @Since: 1.0
 */
@RestController
public class TestController {
    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate;
    @Resource
    private AdminClient adminClient;
    @GetMapping("/test")
    public String test() throws InterruptedException {
        ProducerUtil producerUtil = new ProducerUtil(kafkaTemplate, adminClient);
        String msg="hello world";
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(new Runnable() {
            @Override
            public void run() {
                producerUtil.sendMessage("Test333","hello", msg);
            }
        });

        return "success";
    }
}
