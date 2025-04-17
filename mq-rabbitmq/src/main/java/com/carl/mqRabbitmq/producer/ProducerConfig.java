package com.carl.mqRabbitmq.producer;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Configuration
@Slf4j
public class ProducerConfig {
    @Resource
    private ProducerProperties producerProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(producerProperties.getAddress());
        factory.setUsername(producerProperties.getUserName());
        factory.setPassword(producerProperties.getPassWord());
        factory.setConnectionTimeout(producerProperties.getConnectionTimeout());
        factory.setVirtualHost(producerProperties.getVirtualHost());
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        factory.setPublisherReturns(producerProperties.getPublisherReturns());
        return factory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        // 忽略声明异常
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        //当设置为 true 时，表示如果消息无法路由到任何队列（例如没有匹配的绑定键），RabbitMQ会将消息返回给生产者
        rabbitTemplate.setMandatory(true);
        //当我们的消息发送到exchange之后无法正确路由到队列中会触发returnsCallBack
        rabbitTemplate.setReturnsCallback((message)->{
            log.error("消息未路由成功,message= [{}]",message.getMessage().toString());
            log.error("回复码：[{}]" + message.getReplyCode());
            log.error("回复信息：[{}]" + message.getReplyText());
            log.error("交换器：[{}]" + message.getExchange());
            log.error("路由键：[{}]" + message.getRoutingKey());
            //TODO
        });
        //设置生产者重试模板
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        // 开启了生产者确认模式，所以发送完消息后会接收到一个发送结果的消息，
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)->{
            if (ack){
                log.info("消息发送成功，{}",correlationData.getId());
            }else{
                log.error("发送消息失败，失败内容: [{}]",cause.toString());
            }
        });
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}
