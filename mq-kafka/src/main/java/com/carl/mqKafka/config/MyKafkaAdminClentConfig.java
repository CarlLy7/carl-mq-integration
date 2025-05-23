package com.carl.mqKafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * @description: kafka集群admin配置
 * @author: carl
 * @date: 2025.04.15
 * @Since: 1.0
 */
@Configuration
public class MyKafkaAdminClentConfig {
    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,  "192.168.20.128:9092,192.168.20.128:9093,192.168.20.128:9094");
        return new KafkaAdmin(configs);
    }

    @Bean
    public AdminClient adminClient(KafkaAdmin admin) {
        return AdminClient.create(admin.getConfigurationProperties());
    }
}
