package io.samples.web;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/7/28
 */
@Slf4j
@Service
public class KafkaConsumerListener {

    @Value("${kafka.host:192.168.1.16:9092}")
    private String host;
    @Value("${kafka.topic:topics-test}")
    private String topic;
    @Value("${kafka.consumer.group:training-group}")
    private String consumerGroup;
    private KafkaConsumer<String, String> kafkaConsumer;

    @PostConstruct
    private void init() {
        Properties p = new Properties();
        p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        p.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);

        kafkaConsumer = new KafkaConsumer<>(p);
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        Executors.newSingleThreadExecutor().submit(() -> {
            listen();
        });
    }

    public void listen() {
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ZERO.withSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                log.info("consumer group:{}, receive message, topic: {}, offset:{}, content:{}", consumerGroup, record.topic(), record.offset(), record.value());
            }
        }
    }
}
