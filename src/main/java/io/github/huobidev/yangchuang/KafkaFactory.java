package io.github.huobidev.yangchuang;

import java.util.Properties;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaFactory {


    static String topicName = "topic-yc";
    static String key = "order";


    static Properties producerPro = new Properties();
    static Properties consumerPro = new Properties();

    private static KafkaProducer kafkaProducer;
    private static KafkaConsumer kafkaConsumer;

    static {
        String servers = "127.0.0.1:9092";
        producerPro.put("bootstrap.servers", servers);
        producerPro.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerPro.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerPro.put("max.in.flight.requests.per.connection", "1");
        producerPro.put("ack", "all");
        producerPro.put("enable.idempotence", "true");
        producerPro.put("retries", 3);
        producerPro.put("transactional.id", "send.order.transaction.id");

        consumerPro.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerPro.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerPro.put("bootstrap.servers", servers);
        consumerPro.put("group.id", "group1");
        consumerPro.put("enable.auto.commit", "true");
        consumerPro.put("auto.commit.interval.ms", "5000");

    }

    static KafkaProducer buildProducer() {
        if (kafkaProducer == null) {
            kafkaProducer = new KafkaProducer(producerPro);
            kafkaProducer.initTransactions();
        }
        return kafkaProducer;
    }

    static KafkaConsumer buildConsumer() {
        if (kafkaConsumer == null) {
            kafkaConsumer = new KafkaConsumer(consumerPro);
        }
        return kafkaConsumer;
    }

}
