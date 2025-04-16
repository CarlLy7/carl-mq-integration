package com.carl.mqKafka.consumer;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: kafka消费者配置
 * @author: carl
 * @date: 2025.04.16
 * @Since: 1.0
 */
@Component
@Slf4j
public class TestConsumer {
    @Resource
    private RetryTemplate retryTemplate;

    /**
     * 指定topic单条消费
     * 
     * @param record
     * @param acknowledgment
     */
    @KafkaListener(topics = "Test222", groupId = "consumer222", containerFactory = "containerFactory")
    public void listenTest222(String record, Acknowledgment acknowledgment) {
        log.info(Thread.currentThread().getName() + ": " + System.currentTimeMillis() + " :单条消费消息，接受到的消息内容为[{}]",
            record);
        // 手动提交ack
        acknowledgment.acknowledge();
    }

    /**
     * 指定topic批量消费
     * 
     * @param records
     * @param acknowledgment
     */
    @KafkaListener(topics = "Test222", groupId = "consumer2Batch", containerFactory = "containerFactory")
    public void listenTest222Batch(List<String> records, Acknowledgment acknowledgment) {
        for (String record : records) {
            log.info(Thread.currentThread().getName() + ": " + System.currentTimeMillis() + " :单条消费消息，接受到的消息内容为[{}]",
                record);
            // 手动提交ack
            acknowledgment.acknowledge();
        }
    }

    /**
     * 指定分区批量消费
     * 
     * @param
     * @param acknowledgment
     */
    @KafkaListener(topics = "Test333", groupId = "consumer333", containerFactory = "containerFactory",
        topicPartitions = {@TopicPartition(topic = "Test333", partitions = {"1"})})
    public void listenTest333Batch(List<String> records, Acknowledgment acknowledgment) {
        for (String record : records) {
            log.info(Thread.currentThread().getName() + ": " + System.currentTimeMillis() + " :多条消费消息，接受到的消息内容为[{}]",
                record);
            if (record.contains("zero")) {
                try {
                    int a = 1 / 0;
                } catch (ArithmeticException e) {

                } finally {
                    // 手动提交ack
                    acknowledgment.acknowledge();
                }
            }
        }
    }


    /**
     * 结合retry实现重试, 不使用@RetryableTopic进行重试的理由：@RetryableTopic不支持批量监听的listener
     * @param
     * @param acknowledgment
     */
    @KafkaListener(containerFactory = "containerFactory",topics = "Test333", groupId = "consumer444",topicPartitions = {
            @TopicPartition(topic = "Test333",partitions = {"2"})
    })
    public void listenTest333(List<ConsumerRecord<String,Object>> records, Acknowledgment acknowledgment) {
        log.info("批量拉取的数量：{}",records.size());
        for (ConsumerRecord<String,Object> record : records) {
            log.info(Thread.currentThread().getName() + ": " + LocalDateTime.now() + " :单条消费消息，接受到的消息内容为[{}]",
                    record);
            if (record.value().toString().contains("zero")) {
                try {
                    /**
                     * String：返回类型
                     * Exception：可能抛出的异常
                     */
                    retryTemplate.execute(new RetryCallback<String, Exception>() {
                        @Override
                        public String doWithRetry(RetryContext context) throws Exception {
                            log.info("我正在重试....重试次数: [{}]", context.getRetryCount());
                            try {
                                if (context.getRetryCount() == 0) {
                                    int a = 1 / 0;
                                }
                            } catch (ArithmeticException e) {
                                throw e;
                            }
                            return "success";
                        }
                    });
                } catch (Exception e) {
                    log.error("重试失败");
                }finally {
                    acknowledgment.acknowledge();
                }
            }
        }
    }

    /**
     * 单条处理dlt中的消息
     * 
     * @param record
     * @param
     */
    @DltHandler
    public void handleDltMessage(String record) {
        log.info("死信topic中的消息为：[{}]",record);
        throw new ArithmeticException();
//        acknowledgment.acknowledge();
    }
}
