package io.github.huobidev.zhangwei;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import io.github.huobidev.Order;

public class ProducerImpl implements Producer {
    private KafkaProducer producer = new KafkaProducer(KafkaConfig.getProducerConfig());

    @Override
    public void produce(String topic, Order order) {
        producer.send(new ProducerRecord(topic, JSON.toJSONString(order)));
        producer.flush();
    }
}
