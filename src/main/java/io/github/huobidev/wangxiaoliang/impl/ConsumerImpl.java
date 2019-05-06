package io.github.huobidev.wangxiaoliang.impl;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import io.github.huobidev.wangxiaoliang.Consumer;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;


public class ConsumerImpl implements Consumer {

    private KafkaConsumer kafkaConsumer;

    public KafkaConsumer getConsumer() {
        if (kafkaConsumer == null) {
            Properties consumerPro = new Properties();
            consumerPro.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumerPro.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumerPro.put("bootstrap.servers", "127.0.0.1:9092");
            consumerPro.put("group.id", "group1");
            consumerPro.put("enable.auto.commit", "true");
            consumerPro.put("auto.commit.interval.ms", "5000");
            kafkaConsumer = new KafkaConsumer(consumerPro);
        }
        return kafkaConsumer;
    }

    @Override
    public void consumer() {
        consumer(null);
    }

    @Override
    public void consumer(Long offset) {

        kafkaConsumer = this.getConsumer();
        kafkaConsumer.poll(0);
        if (offset != null) {
            kafkaConsumer.seek(new TopicPartition("topic-name-test", 0), offset);
        } else {
            kafkaConsumer.seek(new TopicPartition("topic-name-test", 0), readFromDB());

        }

        while (true) {

            ConsumerRecords poll = kafkaConsumer.poll(100L);
            poll.forEach(o -> {
                ConsumerRecord<String, String> record = (ConsumerRecord) o;
                Order order = JSON.parseObject(record.value(), Order.class);
                System.err.println("order : " + order);
                saveOffsetInDB(record.offset());
            });
        }

    }

    private Long readFromDB() {
        return 1000L;
    }

    private void saveOffsetInDB(Long offset) {
        System.err.println("save offset : " + offset);
    }
}
