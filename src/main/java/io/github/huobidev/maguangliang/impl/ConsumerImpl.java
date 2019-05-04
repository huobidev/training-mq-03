package io.github.huobidev.maguangliang.impl;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.github.huobidev.Order;
import io.github.huobidev.maguangliang.Consumer;
import io.github.huobidev.maguangliang.KafkaConfig;

public class ConsumerImpl implements Consumer {

    private KafkaConsumer consumer;

    private Map<String, String> cache = new HashMap<>();

    @Override
    public void init() {
        Properties pops = new Properties();
        pops.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        pops.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        pops.put("bootstrap.servers", String.format("%s:%s", KafkaConfig.host, KafkaConfig.port));
        pops.put("group.id", "consumer1");
        consumer = new KafkaConsumer(pops);
    }

    @Override
    public List<Order> receive(String topic, Long offset) {
        if (offset != null) {
            consumer.seek(new TopicPartition(topic, 0), offset);
        } else {
            consumer.subscribe(Arrays.asList(topic));
        }
        ConsumerRecords<String, String> records = consumer.poll(KafkaConfig.timeout);

        List<Order> orderList = new ArrayList<>();
        for (ConsumerRecord<String, String> record : records) {
            Order order = JSON.parseObject(record.value(), Order.class);
            if (cache.containsKey(order.getId() + order.getSymbol())) {
                continue;
            }
            orderList.add(order);
            cache.put(order.getId() + order.getSymbol(), order.getSymbol());
        }

        return orderList;
    }

    @Override
    public void close() {
        if (consumer != null) {
            consumer.close();
        }
    }
}
