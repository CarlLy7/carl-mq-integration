package com.carl.mqRabbitmq.producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.carl.mqRabbitmq.constants.ExchangeConstants;
import com.carl.mqRabbitmq.constants.QueueConstants;
import com.carl.mqRabbitmq.constants.RoutingKeyConstants;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Component
public class QueueConfig {
    @Bean
    public Queue directQueue(){
        //持久化队列
        return new Queue(QueueConstants.DIRECT_QUEUE,true,false,false);
    }

    @Bean
    public Queue dltQueue(){
        //持久化队列
        return new Queue(QueueConstants.DLT_QUEUE,true,false,false);
    }

    @Bean
    public Queue delayQueue(){
        Map<String, Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange", ExchangeConstants.DLT_EXCHANGE);
        args.put("x-dead-letter-routing-key", RoutingKeyConstants.DLT_EXCHANGE_TO_DLT_QUEUE);
        args.put("x-message-ttl",5000);
        //持久化队列
        return new Queue(QueueConstants.DELAY_QUEUE,true,false,false,args);
    }

}
