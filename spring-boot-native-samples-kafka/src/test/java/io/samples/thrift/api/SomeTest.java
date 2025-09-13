package io.samples.thrift.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/25
 */
@Slf4j
public class SomeTest {

    @Test
    void test() {
//        Properties props = new Properties();
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.16:9092");
//        props.put(AdminClientConfig.RETRIES_CONFIG, 0);
//        props.put("batch.size", 16384);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        AdminClient create = KafkaAdminClient.create(props);
//        List<NewTopic> newTopics = new ArrayList<>();
//        NewTopic newTopic = new NewTopic("test-topic", 1, (short)1);
//        newTopics.add(newTopic);
//        create.createTopics(newTopics);
//        create.close();
    }

}
