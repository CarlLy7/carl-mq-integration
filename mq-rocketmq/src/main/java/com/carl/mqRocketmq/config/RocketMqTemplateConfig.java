package com.carl.mqRocketmq.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.18
 * @Since: 1.0
 */
@Configuration
public class RocketMqTemplateConfig {
    /**
     * 默认rocketMQTemplate
     * @return
     */
    @Bean("rocketMQTemplate")
    @Primary
    public RocketMQTemplate rocketMQTemplate(){
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup("carl111");
        producer.setSendMsgTimeout(3000);
        producer.setNamesrvAddr("192.168.20.128:9876");
        producer.setRetryTimesWhenSendFailed(3);
        producer.setRetryTimesWhenSendAsyncFailed(3);
        producer.setRetryAnotherBrokerWhenNotStoreOK(false);
        // 消息最大长度，单位字节
        producer.setMaxMessageSize(1024*32);
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        rocketMQTemplate.setProducer(producer);
        return rocketMQTemplate;
    }


    /**
     * 用来进行半事务提交的生产者RocketmqTemplate配置
     * @return
     */
    @Bean("transactionRocketmqTemplate")
    public RocketMQTemplate transactionRocketmqTemplate(){
        TransactionMQProducer producer = new TransactionMQProducer("carl-transaction");
        producer.setNamesrvAddr("192.168.20.128:9876");
        producer.setRetryTimesWhenSendFailed(3);
        producer.setRetryTimesWhenSendAsyncFailed(3);
        producer.setRetryAnotherBrokerWhenNotStoreOK(false);
        // 消息最大长度，单位字节
        producer.setMaxMessageSize(1024*32);
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        rocketMQTemplate.setProducer(producer);
        return rocketMQTemplate;
    }


    /**
     * 定义的其他生产者组的rocketMQTemplate
     * @return
     */
    @Bean("rocketMQTemplate222")
    public RocketMQTemplate rocketMQTemplate222(){
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup("carl222");
        producer.setSendMsgTimeout(3000);
        producer.setNamesrvAddr("192.168.20.128:9876");
        producer.setRetryTimesWhenSendFailed(3);
        producer.setRetryTimesWhenSendAsyncFailed(3);
        producer.setRetryAnotherBrokerWhenNotStoreOK(false);
        // 消息最大长度，单位字节
        producer.setMaxMessageSize(1024*32);
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        rocketMQTemplate.setProducer(producer);
        return rocketMQTemplate;
    }
}
