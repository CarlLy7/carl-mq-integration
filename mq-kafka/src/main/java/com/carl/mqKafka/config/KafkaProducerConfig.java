package com.carl.mqKafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import jakarta.annotation.Resource;
import lombok.Data;

/**
 * @description: kafka生产者配置
 * @author: carl
 * @date: 2025.04.16
 * @Since: 1.0
 */
@Configuration
@Data
public class KafkaProducerConfig {

    @Resource
    private ProducerProperties producerProperties;

    /**
     * 生产者属性基本配置
     * 
     * @return
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.getValueSerializer());
        props.put(ProducerConfig.RETRIES_CONFIG, producerProperties.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producerProperties.getBatchSize());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producerProperties.getBufferMemory());
        props.put(ProducerConfig.ACKS_CONFIG, producerProperties.getAcks());
        Map<String, Object> subProps = new HashMap<>();
        subProps.put("request.timeout.ms", producerProperties.getRequestTimeoutMs());
        subProps.put("enable.idempotence", producerProperties.getEnableIdempotence());
        subProps.put("max.in.flight.requests.per.connection", producerProperties.getMaxInFlightRequestsPerConnection());
        subProps.put("linger.ms", producerProperties.getLingerMs());
        props.put("properties", subProps);
        return props;
    }

    /**
     * 生产者工厂bean
     * 
     * @return
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * 通用kafkaTemplate模板Bean
     * @return
     */
    @Bean("myKafkaTemplate")
    public KafkaTemplate<String, Object> myKafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

}
