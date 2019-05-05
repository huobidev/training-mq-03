package io.github.huobidev.yangchuang;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class ConsumerImpl implements Consumer {


    @Override
    public void consumer() {
        consumer(null);
    }

    @Override
    public void consumer(Long offset) {

        KafkaConsumer kafkaConsumer = KafkaFactory.buildConsumer();

        if (offset != null) {
            kafkaConsumer.seek(new TopicPartition(KafkaFactory.topicName, 0), offset);
        } else {
            kafkaConsumer.seek(new TopicPartition(KafkaFactory.topicName, 0), readFromDB());

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

    public static void main(String[] args) {
        Consumer consumer = new ConsumerImpl();
        new Thread(consumer::consumer).run();
    }
}
