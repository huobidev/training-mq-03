package io.github.huobidev.zhangwei;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRY_BACKOFF_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

public class KafkaConfig {
    public static final long POLL_TIMEOUT = 5000l;

    public static Properties getProducerConfig() {
        Properties producerConfig = new Properties();
        producerConfig.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        producerConfig.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put(ENABLE_IDEMPOTENCE_CONFIG, true);
        // 完全确认
        producerConfig.put(ACKS_CONFIG, "all");
        producerConfig.put(RETRIES_CONFIG, 3);
        producerConfig.put(RETRY_BACKOFF_MS_CONFIG, 100);
        producerConfig.put(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        return producerConfig;
    }


    public static Properties getConsumerConfig() {
        Properties consumerConfig = new Properties();
        consumerConfig.setProperty(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        consumerConfig.setProperty(GROUP_ID_CONFIG, "btcusdt");
        consumerConfig.setProperty(ENABLE_AUTO_COMMIT_CONFIG, "true");
        consumerConfig.setProperty(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        consumerConfig.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfig.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerConfig.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        return consumerConfig;
    }


}
