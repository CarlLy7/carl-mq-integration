package com.carl.mqRocketmq.producer;

import java.util.List;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: carl
 * @date: 2025.04.18
 * @Since: 1.0
 */
@Slf4j
public class ProducerUtil {
    private final RocketMQTemplate rocketMQTemplate;

    public ProducerUtil(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 同步发送json格式的数据
     * 
     * @param destination
     * @param msg
     * @return
     */
    public Boolean syncSend(String destination, String msg) {
        log.info("发送给目的地: [{}], 消息内容为: [{}]", destination, msg);
        SendResult sendResult = rocketMQTemplate.syncSend(destination, msg);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }

    /**
     * 同步发送object格式的数据
     * 
     * @param destination
     * @param data
     * @return
     */
    public Boolean syncSend(String destination, Object data) {
        log.info("发送给目的地: [{}], 消息内容为: [{}]", destination, data);
        SendResult sendResult = rocketMQTemplate.syncSend(destination, data);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }

    /**
     * 批量同步发送json格式的数据
     * 
     * @param destination
     * @param msgs
     * @return
     */
    public Boolean syncSend(String destination, String[] msgs) {
        log.info("批量发送给目的地: [{}]", destination);
        SendResult sendResult = rocketMQTemplate.syncSend(destination, msgs);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }

    /**
     * 批量同步发送object格式的数据
     * 
     * @param destination
     * @param datas
     * @return
     */
    public Boolean syncSend(String destination, List<Object> datas) {
        log.info("批量发送给目的地: [{}]", destination);
        SendResult sendResult = rocketMQTemplate.syncSend(destination, datas);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }

    // *****************************************
    /**
     * 异步发送json格式的数据
     * 
     * @param destination
     * @param msg
     * @return
     */
    public void asyncSend(String destination, String msg) {
        log.info("发送给目的地: [{}], 消息内容为: [{}]", destination, msg);
        rocketMQTemplate.asyncSend(destination, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                    log.info("消息发送成功了");
                }else{
                    log.error("消息发送失败了,消息id= [{}]", sendResult.getMsgId());
                }
            }

            @Override
            public void onException(Throwable e) {
                log.error("消息发送失败了", e);
            }
        });
    }

    /**
     * 异步发送object格式的数据
     * 
     * @param destination
     * @param data
     * @return
     */
    public void asyncSend(String destination, Object data) {
        log.info("发送给目的地: [{}], 消息内容为: [{}]", destination, data);
        rocketMQTemplate.asyncSend(destination, data, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                    log.info("消息发送成功了");
                }else{
                    log.error("消息发送失败了,消息id= [{}]", sendResult.getMsgId());
                }
            }

            @Override
            public void onException(Throwable e) {
                log.error("消息发送失败了", e);
            }
        });
    }

    /**
     * 批量异步发送json格式的数据
     * 
     * @param destination
     * @param msgs
     * @return
     */
    public void asyncSend(String destination, String[] msgs) {
        log.info("批量发送给目的地: [{}]", destination);
        rocketMQTemplate.asyncSend(destination, msgs, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                    log.info("消息发送成功了");
                }else{
                    log.error("消息发送失败了,消息id= [{}]", sendResult.getMsgId());
                }
            }

            @Override
            public void onException(Throwable e) {
                log.error("消息发送失败了", e);
            }
        });
    }

    /**
     * 批量异步发送object格式的数据
     * 
     * @param destination
     * @param datas
     * @return
     */
    public void asyncSend(String destination, List<Object> datas) {
        log.info("批量发送给目的地: [{}]", destination);
        rocketMQTemplate.asyncSend(destination, datas, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                    log.info("消息发送成功了");
                }else{
                    log.error("消息发送失败了,消息id= [{}]", sendResult.getMsgId());
                }
            }

            @Override
            public void onException(Throwable e) {
                log.error("消息发送失败了", e);
            }
        });
    }

    /**
     * 事务发送json格式的消息
     * @param destination
     * @param msg
     * @return
     */

    public Boolean sendTransaction(String destination,String msg){
        log.info("发送事务消息，destination is [{}], msg is [{}]",destination,msg);
        Message<String> message = MessageBuilder.withPayload(msg).build();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination,message, null);
        return sendResult.getSendStatus()==SendStatus.SEND_OK;
    }

}
