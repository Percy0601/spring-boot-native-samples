package io.samples.mybatis;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/7/25
 */
@Slf4j
@Service
public class MessageService {

    @Value("${rocketmq.server.address:192.168.1.12:8081}")
    private String endpoint;
    @Value("${rocketmq.server.topic:TestTopic}")
    private String topic;

    @Value("${rocketmq.consumer.group:YourConsumerGroup}")
    private String consumerGroup;
    private Producer producer;
    private ClientServiceProvider provider;

    @PostConstruct
    public void init() {
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081。
        provider = ClientServiceProvider.loadService();
        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().
                setEndpoints(endpoint);
        ClientConfiguration configuration = builder.build();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        try {
            producer = provider.newProducerBuilder()
                    .setTopics(topic)
                    .setClientConfiguration(configuration)
                    .build();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }

        // producer.close();



        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8081;xxx:8081。
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoint)
                .build();
        // 订阅消息的过滤规则，表示订阅所有Tag的消息。
        String tag = "*";
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);

        // 初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
        try {
            PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                    .setClientConfiguration(clientConfiguration)
                    // 设置消费者分组。
                    .setConsumerGroup(consumerGroup)
                    // 设置预绑定的订阅关系。
                    .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                    // 设置消费监听器。
                    .setMessageListener(messageView -> {
                        // 处理消息并返回消费结果。
                        ByteBuffer byteBuffer = messageView.getBody();
                        byte[] bytes = new byte[byteBuffer.remaining()];
                        byteBuffer.get(bytes);
                        log.info("Consume message successfully, messageId={}, body:{}", messageView.getMessageId(), new String(bytes, StandardCharsets.UTF_8));
                        return ConsumeResult.SUCCESS;
                    })
                    .build();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(3L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 如果不需要再使用 PushConsumer，可关闭该实例。
        // pushConsumer.close();
    }


    public SendReceipt send(String msg) {
        // 普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(topic)
                // 设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(UUID.randomUUID().toString())
                // 设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag("messageTag")
                // 消息体。
                .setBody(msg.getBytes(StandardCharsets.UTF_8))
                .build();
        SendReceipt sendReceipt = null;
        try {
            // 发送消息，需要关注发送结果，并捕获失败等异常。
            sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            log.error("Failed to send message", e);
        }
        return sendReceipt;
    }


    public void receive() {


    }


}
