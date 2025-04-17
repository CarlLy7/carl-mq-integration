package com.carl.mqRabbitmq.producer;

import com.carl.mqRabbitmq.constants.RoutingKeyConstants;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Component
public class BindingConfig {
    @Resource
    @Qualifier("directExchange")
    private Exchange directExchange;

    @Resource
    @Qualifier("directQueue")
    private Queue directQueue;

    @Resource
    @Qualifier("dltExchange")
    private Exchange dltExchange;

    @Resource
    @Qualifier("dltQueue")
    private Queue dltQueue;

    @Resource
    @Qualifier("delayExchange")
    private Exchange delayExchange;

    @Resource
    @Qualifier("delayQueue")
    private Queue delayQueue;

    @Bean
    public Binding directExchangeToDirectQueue() {
        return BindingBuilder.bind(directQueue).to(directExchange)
            .with(RoutingKeyConstants.DIRECT_EXCHANGE_TO_DIRECT_QUEUE).noargs();
    }

    @Bean
    public Binding dltExchangeToDltQueue() {
        return BindingBuilder.bind(dltQueue).to(dltExchange)
                .with(RoutingKeyConstants.DLT_EXCHANGE_TO_DLT_QUEUE).noargs();
    }

    @Bean
    public Binding delayExchangeToDelayQueue() {
        return BindingBuilder.bind(delayQueue).to(delayExchange)
                .with(RoutingKeyConstants.DELAY_EXCHANGE_TO_DELAYT_QUEUE).noargs();
    }
}
