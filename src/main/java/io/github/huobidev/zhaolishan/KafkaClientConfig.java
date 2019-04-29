package io.github.huobidev.zhaolishan;

import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRY_BACKOFF_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class KafkaClientConfig {

    private static Properties producerProps = new Properties();
    private static Properties consumerProps = new Properties();

    private KafkaClientConfig() {
    }

    static {
        //produce
        producerProps.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        producerProps.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put(ENABLE_IDEMPOTENCE_CONFIG, true);
        producerProps.put(ACKS_CONFIG, "all");
        producerProps.put(RETRIES_CONFIG, 5);
        producerProps.put(RETRY_BACKOFF_MS_CONFIG, 100);
        producerProps.put(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        //consume
        consumerProps.setProperty(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        consumerProps.setProperty(GROUP_ID_CONFIG, "zhaolishan");
        consumerProps.setProperty(ENABLE_AUTO_COMMIT_CONFIG, "true");
        consumerProps.setProperty(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        consumerProps.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public static Properties getProducerProps() {
        return producerProps;
    }

    public static Properties getConsumerProps() {
        return consumerProps;
    }
}
