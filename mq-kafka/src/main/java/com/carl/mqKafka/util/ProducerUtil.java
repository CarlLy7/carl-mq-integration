package com.carl.mqKafka.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.alibaba.fastjson2.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: kafka发送消息工具类
 * @author: carl
 * @date: 2025.04.15
 * @Since: 1.0
 */
@Slf4j
public class ProducerUtil {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AdminClient adminClient;

    public ProducerUtil(KafkaTemplate<String, Object> kafkaTemplate, AdminClient adminClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.adminClient = adminClient;
    }

    /**
     * 判断是否已经存在topic了，如果有直接返回true,没有的话则创建topic
     * 
     * @param topicName topic的名字
     * @param partitionNum 分区的数量
     * @param replicaNum 副本的数量
     * @return true or false
     */
    public Boolean createTopic(String topicName, int partitionNum, int replicaNum) {
        KafkaFuture<Set<String>> topics = adminClient.listTopics().names();
        try {
            if (topics.get().contains(topicName)) {
                return true;
            }
            // 创建topic
            NewTopic newTopic = new NewTopic(topicName, partitionNum, (short)replicaNum);
            adminClient.createTopics(Collections.singleton(newTopic));
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 发送json格式且不包含key的消息给topic中
     * 
     * @param topicName
     * @param jsonStr
     * @return true or false
     */
    public boolean sendMessage(String topicName, String jsonStr) {
        createTopic(topicName, 3, 3);
        log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis() + " 发送消息给Topic=[{}],发送的消息内容为:[{}]",
            topicName, jsonStr);
        CompletableFuture<SendResult<String, Object>> sendResultFuture =
            kafkaTemplate.send(new ProducerRecord<>(topicName, jsonStr));
        return dealSendResult(sendResultFuture);
    }

    /**
     * 批量发送json格式且不包含key的消息给topic中
     * 
     * @param topicName
     * @param jsonStrs
     * @return
     */
    public boolean sendMessage(String topicName, String[] jsonStrs) {
        createTopic(topicName, 3, 3);
        boolean result = false;
        for (int i = 0; i < jsonStrs.length; i++) {
            String jsonStr = jsonStrs[i];
            log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis()
                + " 批量发送消息给Topic=[{}],发送的消息内容为:[{}]", topicName, jsonStr);
            CompletableFuture<SendResult<String, Object>> sendResultFuture =
                kafkaTemplate.send(new ProducerRecord<>(topicName, jsonStr));
            result = dealSendResult(sendResultFuture);
        }
        return result;
    }

    /**
     * 发送Object格式且不包含key的消息给topic中
     * 
     * @param topicName
     * @param data
     * @return
     */
    public boolean sendMessage(String topicName, Object data) {
        createTopic(topicName, 3, 3);
        log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis() + " 发送消息给Topic=[{}],发送的消息内容为:[{}]",
            topicName, JSONObject.toJSONString(data));
        CompletableFuture<SendResult<String, Object>> sendResultFuture =
            kafkaTemplate.send(new ProducerRecord<>(topicName, data));
        return dealSendResult(sendResultFuture);
    }

    /**
     * 批量发送Object格式且不包含key的消息给topic中
     * 
     * @param topicName
     * @param datas
     * @return
     */
    public boolean sendMessage(String topicName, List<Object> datas) {
        createTopic(topicName, 3, 3);
        boolean result = false;
        for (int i = 0; i < datas.size(); i++) {
            Object data = datas.get(i);

            log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis()
                + " 批量发送消息给Topic=[{}],发送的消息内容为:[{}]", topicName, JSONObject.toJSONString(data));

            CompletableFuture<SendResult<String, Object>> sendResultFuture =
                kafkaTemplate.send(new ProducerRecord<>(topicName, data));
            result = dealSendResult(sendResultFuture);
        }
        return result;
    }

    /**
     * 发送格式为json,包含key的消息
     * 
     * @param topicName
     * @param key
     * @param jsonStr
     * @return
     */
    public boolean sendMessage(String topicName, String key, String jsonStr) {
        createTopic(topicName, 3, 3);
        Object data = JSONObject.parseObject(jsonStr, Object.class);
        log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis()
            + " 发送消息给Topic=[{}],key=[{}],发送的消息内容为:[{}]", topicName, key, jsonStr);
        CompletableFuture<SendResult<String, Object>> sendResultFuture =
            kafkaTemplate.send(new ProducerRecord<>(topicName, key, data));
        return dealSendResult(sendResultFuture);
    }

    /**
     * 批量发送格式为json,包含key的消息
     * 
     * @param topicName
     * @param key
     * @param jsonStrs
     * @return
     */
    public boolean sendMessage(String topicName, String key, String[] jsonStrs) {
        createTopic(topicName, 3, 3);
        boolean result = false;
        for (int i = 0; i < jsonStrs.length; i++) {
            String jsonStr = jsonStrs[i];
            Object data = JSONObject.parseObject(jsonStr, Object.class);
            log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis()
                + " 批量发送消息给Topic=[{}],key=[{}],发送的消息内容为:[{}]", topicName, key, jsonStr);
            CompletableFuture<SendResult<String, Object>> sendResultFuture =
                kafkaTemplate.send(new ProducerRecord<>(topicName, key, data));
            result = dealSendResult(sendResultFuture);
        }
        return result;
    }

    /**
     * 发送格式为object,包含key的消息
     * 
     * @param topicName
     * @param key
     * @param data
     * @return
     */
    public boolean sendMessage(String topicName, String key, Object data) {
        createTopic(topicName, 3, 3);
        log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis()
            + " 发送消息给Topic=[{}],key=[{}],发送的消息内容为:[{}]", topicName, key, JSONObject.toJSONString(data));
        CompletableFuture<SendResult<String, Object>> sendResultFuture =
            kafkaTemplate.send(new ProducerRecord<>(topicName, key, data));
        return dealSendResult(sendResultFuture);
    }

    /**
     * 批量发送格式为object,包含key的消息
     * 
     * @param topicName
     * @param key
     * @param datas
     * @return
     */
    public boolean sendMessage(String topicName, String key, List<Object> datas) {
        createTopic(topicName, 3, 3);
        boolean result = false;
        for (Object data : datas) {
            log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis()
                + " 批量发送消息给Topic=[{}],key=[{}],发送的消息内容为:[{}]", topicName, key, JSONObject.toJSONString(data));
            CompletableFuture<SendResult<String, Object>> sendResultFuture =
                kafkaTemplate.send(new ProducerRecord<>(topicName, key, data));
            result = dealSendResult(sendResultFuture);
        }
        return result;
    }

    /**
     * 指定topic、指定分区、指定key发送json格式的消息
     * 
     * @param topicName
     * @param partition
     * @param key
     * @param jsonStr
     * @return
     */
    public boolean sendMessage(String topicName, Integer partition, String key, String jsonStr) {
        createTopic(topicName, 3, 3);
        log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis()
            + " 发送消息给Topic=[{}],partition= [{}], key=[{}],发送的消息内容为:[{}]", topicName, partition, key, jsonStr);
        CompletableFuture<SendResult<String, Object>> sendResultFuture =
            kafkaTemplate.send(new ProducerRecord<>(topicName, partition, key, jsonStr));
        return dealSendResult(sendResultFuture);
    }

    /**
     * 指定topic、指定分区、指定key 批量发送 json格式的消息
     * 
     * @param topicName
     * @param partition
     * @param key
     * @param jsonStrs
     * @return
     */
    public boolean sendMessage(String topicName, Integer partition, String key, String[] jsonStrs) {
        createTopic(topicName, 3, 3);
        boolean result = false;
        for (int i = 0; i < jsonStrs.length; i++) {
            String jsonStr = jsonStrs[i];
            log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis()
                + " 批量发送消息给Topic=[{}],partition= [{}], key=[{}],发送的消息内容为:[{}]", topicName, partition, key, jsonStr);
            CompletableFuture<SendResult<String, Object>> sendResultFuture =
                kafkaTemplate.send(new ProducerRecord<>(topicName, partition, key, jsonStr));
            result = dealSendResult(sendResultFuture);
        }
        return result;
    }

    /**
     * 指定topic、指定分区、指定key发送object格式的消息
     * 
     * @param topicName
     * @param partition
     * @param key
     * @param data
     * @return
     */
    public boolean sendMessage(String topicName, Integer partition, String key, Object data) {
        createTopic(topicName, 3, 3);
        log.info(
            Thread.currentThread().getName() + ":" + System.currentTimeMillis()
                + " 发送消息给Topic=[{}],partition= [{}], key=[{}],发送的消息内容为:[{}]" , topicName,
            partition, key, JSONObject.toJSONString(data));
        CompletableFuture<SendResult<String, Object>> sendResultFuture =
            kafkaTemplate.send(new ProducerRecord<>(topicName, partition, key, data));
        return dealSendResult(sendResultFuture);
    }

    /**
     * 指定topic、指定分区、指定key 批量发送object格式的消息
     * 
     * @param topicName
     * @param partition
     * @param key
     * @param datas
     * @return
     */
    public boolean sendMessage(String topicName, Integer partition, String key, List<Object> datas) {
        createTopic(topicName, 3, 3);
        boolean result = false;
        for (Object data : datas) {
            log.info(
                Thread.currentThread().getName() + ":" + System.currentTimeMillis()
                    + " 批量发送消息给Topic=[{}],partition= [{}], key=[{}],发送的消息内容为:[{}]" , topicName,
                partition, key, JSONObject.toJSONString(data));
            CompletableFuture<SendResult<String, Object>> sendResultFuture =
                kafkaTemplate.send(new ProducerRecord<>(topicName, partition, key, data));
            result = dealSendResult(sendResultFuture);
        }
        return result;
    }

    /**
     * 处理发送结果
     * 
     * @param future
     * @return
     */
    private boolean dealSendResult(CompletableFuture<SendResult<String, Object>> future) {
        String topic = "";
        try {
            SendResult<String, Object> result = future.join();
            topic = result.getProducerRecord().topic();
            return true;
        } catch (CancellationException | CompletionException e) {
            log.error("topic= {} send fail result = {}", topic, e.getMessage());
            return false;
        }
    }
}
