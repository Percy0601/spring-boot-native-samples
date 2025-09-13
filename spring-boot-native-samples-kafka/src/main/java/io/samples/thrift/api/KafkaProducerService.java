package io.samples.thrift.api;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
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
public class KafkaProducerService {

    @Value("${kafka.host:192.168.1.16:9092}")
    private String host;
    @Value("${kafka.topic:topics-test}")
    private String topic;

    private KafkaProducer<String, String> kafkaProducer;

    @PostConstruct
    private void init() {
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host);//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProducer = new KafkaProducer<>(p);
    }

    public Future<RecordMetadata> send(String message){
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        Future<RecordMetadata> result = kafkaProducer.send(record);
        return result;
    }

}
