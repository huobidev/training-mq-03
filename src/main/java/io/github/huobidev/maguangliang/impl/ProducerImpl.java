package io.github.huobidev.maguangliang.impl;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import io.github.huobidev.Order;
import io.github.huobidev.maguangliang.KafkaConfig;
import io.github.huobidev.maguangliang.Producer;

public class ProducerImpl implements Producer {

    private KafkaProducer producer;

    @Override
    public void init() {
        Properties pops = new Properties();
        pops.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pops.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pops.put("bootstrap.servers", String.format("%s:%s", KafkaConfig.host, KafkaConfig.port));

        // 顺序处理
        pops.put("max.in.flight.requests.per.connection", "1");
        pops.put("acks", "all");
        // 幂等
        pops.put("enable.idempotence", "true");
        producer = new KafkaProducer(pops);
    }

    @Override
    public void send(String topic, Order order) {
        producer.send(new ProducerRecord(topic, JSON.toJSONString(order)));
    }

    @Override
    public void close() {
        if (producer != null) {
            producer.close();
        }
    }
}
