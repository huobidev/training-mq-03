package io.github.huobidev.zhaolishan;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.io.Closeable;
import java.util.*;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

public class OrderConsumer implements Consumer<Order>, Closeable {

    private KafkaConsumer<String, String> consumer;
    private long pollTimeOut = 5000L;

    public OrderConsumer(Properties props, String topic) {
        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(Collections.singletonList(topic));
    }

    // from assign offset consume
    public OrderConsumer(Properties props, String topic, String supplyGroup, TopicPartition tp, long offset) {
        props.setProperty(GROUP_ID_CONFIG, supplyGroup);
        this.consumer = new KafkaConsumer<>(props);
        Map<TopicPartition, OffsetAndMetadata> hashMaps = Collections
                .singletonMap(tp, new OffsetAndMetadata(offset));
        this.consumer.commitSync(hashMaps);
        this.consumer.subscribe(Collections.singletonList(topic));
    }

    @Override
    public List<Order> consume() {
        ConsumerRecords<String, String> records = consumer.poll(pollTimeOut);
        List<Order> orders = new ArrayList<>();
        for (ConsumerRecord<String, String> record : records) {
            orders.add(JSON.parseObject(record.value(), Order.class));
        }
        return orders;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
