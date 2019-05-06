package io.github.huobidev.kongdeyuan;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class ConsumerImpl implements Consumer {

    private KafkaConsumer consumer;
    private Set<String> consumedMsg = new HashSet<>();

    public ConsumerImpl() {
        Properties props = new Properties();
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "2000");

        consumer = new KafkaConsumer(props);
        consumer.assign(Arrays.asList(new TopicPartition("topic", 0)));
    }

    @Override
    public void receive() {
        this.receive(0L);
    }

    @Override
    public void receive(Long offset) {
        consumer.seek(new TopicPartition("topic", 0), offset);
        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(1000L);
            poll.forEach(item -> {
                if (consumedMsg.contains(item.key())) {
                    return;
                }
                System.out.println(result(item));
                consumedMsg.add(item.key());
            });
        }
    }

    private String result(ConsumerRecord<String, String> record) {
        return "key = " + record.key() + ", value = " + record.value();
    }

}
