package io.github.huobidev.zhangwei;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.huobidev.Order;

public class ConsumerImpl implements Consumer {

    private KafkaConsumer consumer = new KafkaConsumer(KafkaConfig.getConsumerConfig());

    @Override
    public List<Order> consume(String topic, Long offset) {
        if (offset != null) {
            consumer.seek(new TopicPartition(topic, 0), offset);
        } else {
            consumer.subscribe(Arrays.asList(topic));
        }
        ConsumerRecords<String, String> records = consumer.poll(KafkaConfig.POLL_TIMEOUT);

        List<Order> orders = new ArrayList<>();
        for (ConsumerRecord<String, String> record : records) {
            // todo 1.从redis中取，如果存在则continue,否认则消费

            // 2.消费
            orders.add(JSON.parseObject(record.value(), Order.class));

            // todo 3.更新redis
        }

        return orders;
    }
}
