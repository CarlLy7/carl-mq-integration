package com.carl.mqRocketmq.consumer;

import java.nio.charset.StandardCharsets;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.18
 * @Since: 1.0
 */
@Service
@RocketMQTransactionListener(rocketMQTemplateBeanName = "transactionRocketmqTemplate")
@Slf4j
public class TransactionConsumer implements RocketMQLocalTransactionListener {


    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        //todo 半提交之后，去执行本地的事务
        log.info("接收到的消息为: [{}]",new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8));
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        //todo 用于rocketmq主动来检查本地事务结果
        return RocketMQLocalTransactionState.COMMIT;
    }
}
