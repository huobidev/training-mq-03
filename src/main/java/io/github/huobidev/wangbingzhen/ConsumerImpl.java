package io.github.huobidev.wangbingzhen;

import io.github.huobidev.wangbingzhen.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Properties;

/**
 *
 */
public class ConsumerImpl implements Consumer{

    @Override
    public void receiveMsg(String topic) {
        Properties pro = new Properties();
        pro.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        pro.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        pro.put("bootstrap.servers", "127.0.0.1:9092");
        pro.put("group.id","GRP_A");
        pro.put("enable.auto.commit", "false");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(pro);
        kafkaConsumer.subscribe(Arrays.asList(topic));
        ConsumerRecords consumerRecords = kafkaConsumer.poll(5000);
        consumerRecords.forEach(ckey->{
            ConsumerRecord consumerRecord = (ConsumerRecord) ckey;
            System.out.println(consumerRecord.value());
        });
        kafkaConsumer.close();
    }


}