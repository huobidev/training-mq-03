package io.github.huobidev.chenjun.config;

import java.util.Properties;

/**
 * 配置类
 */
public class KafkaConfig {

    public final static Properties PRODUCER_CONFIG;
    public final static Properties CONSUMER_CONFIG;

    static {
        //生产者
        PRODUCER_CONFIG = new Properties();
        PRODUCER_CONFIG.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        PRODUCER_CONFIG.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        PRODUCER_CONFIG.put("bootstrap.servers", "localhost:9092");
        PRODUCER_CONFIG.put("batch.size", "10240");
        PRODUCER_CONFIG.put("retries", "3");
        PRODUCER_CONFIG.put("linger.ms", 1);
        PRODUCER_CONFIG.put("buffer.memory", "33554432");
        //顺序保证
        PRODUCER_CONFIG.put("max.in.flight.requests.per.connection", "1");
        //开启事务
        PRODUCER_CONFIG.put("acks", "all");
        PRODUCER_CONFIG.put("enable.idempotence", "true");
        PRODUCER_CONFIG.put("transactional.id", "order");

        //消费者
        CONSUMER_CONFIG = new Properties();
        CONSUMER_CONFIG.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        CONSUMER_CONFIG.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        CONSUMER_CONFIG.setProperty("bootstrap.servers", "localhost:9092");
        CONSUMER_CONFIG.setProperty("group.id", "myGroup");

        //自动提交offset
        CONSUMER_CONFIG.put("enable.auto.commit","true");
        CONSUMER_CONFIG.put("auto.commit.interval.ms","5000");
    }
}