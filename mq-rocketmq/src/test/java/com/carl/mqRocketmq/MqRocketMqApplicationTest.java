package com.carl.mqRocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.carl.mqRocketmq.producer.ProducerUtil;

import jakarta.annotation.Resource;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class MqRocketMqApplicationTest{
    @Resource
    @Qualifier("rocketMQTemplate")
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    @Qualifier("transactionRocketmqTemplate")
    private RocketMQTemplate transactionRocketmqTemplate;

    @Resource
    @Qualifier("rocketMQTemplate222")
    private RocketMQTemplate rocketMQTemplate222;
    @Test
    public void testProducer(){
        //同步发送
        rocketMQTemplate.convertAndSend("broker-a:tag1","Hello World");
    }

    @Test
    public void test2(){
        ProducerUtil producerUtil = new ProducerUtil(transactionRocketmqTemplate);
        producerUtil.sendTransaction("broker-b:371904719","Carl is me");
    }

    @Test
    public void test3(){
        ProducerUtil producerUtil = new ProducerUtil(rocketMQTemplate222);
        producerUtil.syncSend("broker-b:carl777","carl777");
    }
}
