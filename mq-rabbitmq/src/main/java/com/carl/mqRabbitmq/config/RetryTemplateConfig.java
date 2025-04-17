package com.carl.mqRabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Configuration
public class RetryTemplateConfig {
    @Bean
    public RetryTemplate retryTemplate(){
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(3);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(2000);  // 第一次重试的时间间隔（1秒）
        backOffPolicy.setMultiplier(2);       // 每次重试的时间间隔乘以 2
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}
