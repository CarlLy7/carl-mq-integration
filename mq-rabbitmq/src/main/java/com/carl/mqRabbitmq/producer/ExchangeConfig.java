package com.carl.mqRabbitmq.producer;

import com.carl.mqRabbitmq.constants.ExchangeConstants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Component
public class ExchangeConfig {
    @Bean
    public Exchange directExchange(){
        //持久化交换机
        DirectExchange directExchange = new DirectExchange(ExchangeConstants.DIRECT_EXCHANGE,true,false);
        return directExchange;
    }

    @Bean
    public Exchange delayExchange(){
        return new DirectExchange(ExchangeConstants.DELAY_EXCHANGE,true,false);
    }

    @Bean
    public Exchange dltExchange(){
        return new DirectExchange(ExchangeConstants.DLT_EXCHANGE,true,false);
    }
}
