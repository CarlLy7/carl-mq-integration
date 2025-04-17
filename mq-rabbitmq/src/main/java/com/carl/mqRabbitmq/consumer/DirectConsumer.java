package com.carl.mqRabbitmq.consumer;

import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import com.carl.mqRabbitmq.constants.QueueConstants;
import com.rabbitmq.client.Channel;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Service
public class DirectConsumer {
    @Resource
    private RetryTemplate retryTemplate;

    @RabbitListener(queues = {QueueConstants.DIRECT_QUEUE}, containerFactory = "listenerContainerFactory")
    public void directConsumer(Message msg, Channel channel) throws Exception {
        try {
            retryTemplate.execute((e) -> {
                System.out.println("我进行消费了");
                int a = 1 / 0;
                return null;
            });
        } catch (Exception e) {  // retry重试完成之后都失败了
            System.out.println("我处理不了了....，扔给死信队列吧");
            //todo 发送到死信队列
            // deliveryTag:channel中消息的唯一标识，单调递增
            channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false,false);
        }
    }
}
