package io.github.huobidev.zhaolishan;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Closeable;
import java.util.Properties;

public class OrderProducer implements Producer<Order>, Closeable {

    private KafkaProducer<String, String> kafkaProducer;
    private String topic;

    public OrderProducer(Properties props, String topic) {
        this.kafkaProducer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void produce(Order order) {
        kafkaProducer.send(new ProducerRecord<>(topic, JSON.toJSONString(order)));
        kafkaProducer.flush();
    }

    @Override
    public void close() {
        kafkaProducer.close();
    }
}
