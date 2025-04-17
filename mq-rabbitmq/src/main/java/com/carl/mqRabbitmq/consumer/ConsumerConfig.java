package com.carl.mqRabbitmq.consumer;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Configuration
public class ConsumerConfig {
    @Resource
    private ConnectionFactory connectionFactory;
    @Resource
    private ConsumerProperties consumerProperties;
    @Bean
    public RabbitListenerContainerFactory listenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //最小消费者数量
        factory.setConcurrentConsumers(consumerProperties.getConcurrency());
        //最大消费者数量
        factory.setMaxConcurrentConsumers(consumerProperties.getMaxConcurrency());
        //一个请求最大处理的消息数量
        factory.setPrefetchCount(consumerProperties.getPrefetch());
        //
//        factory.setChannelTransacted(false);
        //默认不排队
//        factory.setDefaultRequeueRejected(false);
        //手动确认接收到了消息
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置消费者重试策略
//        factory.setRetryTemplate(retryTemplate);
        return factory;
    }
}
