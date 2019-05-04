package io.github.huobidev.chenjun.impl;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import io.github.huobidev.Order;
import io.github.huobidev.chenjun.Consumer;


public class ConsumerImpl implements Consumer {

    private KafkaConsumer<String, String> kafkaConsumer;

    @Override
    public void init(Properties props, String topic, Long offset) {
        kafkaConsumer = new KafkaConsumer<>(props);

        TopicPartition p = new TopicPartition(topic, 0);
        kafkaConsumer.assign(Collections.singletonList(p));
        if (offset != null) {
            kafkaConsumer.seek(p, offset);
        }
    }

    @Override
    public List<Order> consume() {
        ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
        if (consumerRecords.isEmpty()) {
            return Collections.emptyList();
        }
        List<Order> orderList = new ArrayList<>();
        consumerRecords.forEach(record -> orderList.add(JSON.parseObject(record.value(), Order.class)));
        return orderList;
    }
}