package com.carl.mqRocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.18
 * @Since: 1.0
 */
@Service
@Slf4j
@RocketMQMessageListener(topic = "broker-a",
        consumerGroup = "test111",
        //只有顺序消费才可以指定重试的时间间隔
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING,
        maxReconsumeTimes = 3,
        suspendCurrentQueueTimeMillis = 2000
)
public class Consumer implements RocketMQListener<String> {
    private DefaultMQPushConsumer consumer;
    @Override
    public void onMessage(String message) {
        log.info("我来消费了...");
//        String body=new String(message.getBody());
//        log.info("consumer msg = [{}]",body);
//        String brokerName = message.getBrokerName();
//        long queueOffset = message.getQueueOffset();
//        consumer.getOffsetStore()

    }

//    @Override
//    public void prepareStart(DefaultMQPushConsumer consumer) {
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        consumer.setPersistConsumerOffsetInterval(0);
//        this.consumer=consumer;
//    }
}
