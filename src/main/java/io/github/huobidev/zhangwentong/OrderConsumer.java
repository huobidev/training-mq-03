package io.github.huobidev.zhangwentong;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

public class OrderConsumer implements Consumer {

    @Override
    public void ConsumerFromOffset(String topic, Long offset) {
        Properties properties = KafkaProperty.CONSUMER;
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        TopicPartition p = new TopicPartition(topic, 0);
        kafkaConsumer.assign(Collections.singletonList(p));
        if (offset != null) {
            kafkaConsumer.seek(p, offset);
        }
        while (true) {
            ConsumerRecords poll = kafkaConsumer.poll(100L);
            poll.forEach(o -> {
                ConsumerRecord<String, String> record = (ConsumerRecord) o;
                Order order = JSON.parseObject(record.value(), Order.class);
                System.err.println("order : " + order);
                saveStashOffset(record.offset());
            });
        }


    }

    @Override
    public void Consumer(String topic) {
        ConsumerFromOffset(topic, readStashOffset());
    }

    // 获取上次读取到的offset
    private Long readStashOffset() {
        return 1101L;
    }

    // 存储读取到的offset
    private void saveStashOffset(Long offset) {
        // do something
    }
}
