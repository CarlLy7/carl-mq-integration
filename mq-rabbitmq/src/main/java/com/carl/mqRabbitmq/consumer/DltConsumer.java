package com.carl.mqRabbitmq.consumer;

import com.carl.mqRabbitmq.constants.QueueConstants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.17
 * @Since: 1.0
 */
@Service
public class DltConsumer {
    @RabbitListener(queues = QueueConstants.DLT_QUEUE,containerFactory = "listenerContainerFactory")
    public void delayConsumer(Message msg, Channel channel) throws IOException {
        System.out.println("接受到的信息 "+new String(msg.getBody()));
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(),false);
    }
}
