package io.github.huobidev.zhangwentong;

import java.util.Properties;

/**
 * kafka配置类
 */
public class KafkaProperty {

    public final static Properties PRODUCER;
    public final static Properties CONSUMER;

    static {
        /**
         * 生产者配置
         */
        PRODUCER = new Properties();
        PRODUCER.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        PRODUCER.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        PRODUCER.put("bootstrap.servers", "localhost:9092");
        PRODUCER.put("batch.size", "16348");
        PRODUCER.put("linger.ms", 1);
        //  如果设置了retries而没有将max.in.flight.request.per.connection设置为1,
        // 在两个batch发送到同一个partition时有可能打乱消息的发送顺序(第一个发送失败, 而第二个发送成功)
        PRODUCER.put("retries", "3");
        PRODUCER.put("max.in.flight.requests.per.connection", "1");

        PRODUCER.put("buffer.memory", "33554432");

        //开启事务
        PRODUCER.put("acks", "all");
        PRODUCER.put("enable.idempotence", "true");
        PRODUCER.put("transactional.id", "order");

        /**
         *   消费者 配置
         */
        CONSUMER = new Properties();
        CONSUMER.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        CONSUMER.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        CONSUMER.setProperty("bootstrap.servers", "localhost:9092");
        CONSUMER.setProperty("group.id", "myGroup");

        //  offset自动提交
        CONSUMER.put("enable.auto.commit","true");
        CONSUMER.put("auto.commit.interval.ms","5000");
    }
}
