package com.carl.mqRabbitmq;

import java.util.ArrayList;
import java.util.UUID;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import com.carl.mqRabbitmq.constants.ExchangeConstants;
import com.carl.mqRabbitmq.constants.RoutingKeyConstants;
import com.carl.mqRabbitmq.domain.User;

import jakarta.annotation.Resource;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class MqRabbitmqApplicationTest{
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    public void testSendDirect(){
        rabbitTemplate.convertAndSend(ExchangeConstants.DIRECT_EXCHANGE, RoutingKeyConstants.DIRECT_EXCHANGE_TO_DIRECT_QUEUE,"hello world",m->{
            m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return m;
        });
    }

    @Test
    public void testSendDirectPOJO(){
        rabbitTemplate.convertAndSend(ExchangeConstants.DIRECT_EXCHANGE, RoutingKeyConstants.DIRECT_EXCHANGE_TO_DIRECT_QUEUE,new User(UUID.randomUUID().toString(),"carl"), new CorrelationData(UUID.randomUUID().toString()));
    }

    @Test
    public void testBatchSendDirect(){
        ArrayList<String> strings = Lists.newArrayList("hello world1", "hello world2");
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString().substring(0,10));
        rabbitTemplate.convertAndSend(ExchangeConstants.DIRECT_EXCHANGE, RoutingKeyConstants.DIRECT_EXCHANGE_TO_DIRECT_QUEUE,strings,correlationData);
    }

    @Test
    public void testSendToDelay(){
        rabbitTemplate.convertAndSend(ExchangeConstants.DELAY_EXCHANGE,RoutingKeyConstants.DELAY_EXCHANGE_TO_DELAYT_QUEUE,"hello delay",new CorrelationData(UUID.randomUUID().toString()));
    }



}
