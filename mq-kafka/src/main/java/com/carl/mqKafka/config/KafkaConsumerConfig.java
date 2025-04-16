package com.carl.mqKafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * @description: kafka消费者和listener的配置
 * @author: carl
 * @date: 2025.04.16
 * @Since: 1.0
 */
@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "192.168.20.128:9092,192.168.20.128:9093,192.168.20.128:9094");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 批量最大拉取记录数
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        // 服务器应为 fetch 请求返回的最小数据量（单位为字节）
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 500);
        // 它表示消费者在broker端等待满足fetch-min-size条件的最长时间(单位为毫秒)
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);
        //从最早偏移量开始消费（避免漏消息）
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // listener的提交模式为手动立即提交，也就是消费者ack之后立即提交，可以防止消息丢失
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        // 要在listener容器中运行的线程数
        factory.setConcurrency(3);
        // 轮询使consumer时使用的超时时间
        factory.getContainerProperties().setPollTimeout(3000);
        // 是否开启批量消费
        factory.setBatchListener(true);
        return factory;
    }


}
