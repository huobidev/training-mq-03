package io.github.huobidev.fushuai;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConsumerImpl implements Consumer{

    KafkaConsumer<String, String> kafkaConsumer;

    void createConsunmer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("group.id", "group-1");
        properties.put("enable.auto.commit", "false");
        //properties.put("auto.commit.interval.ms", "1000");
        //properties.put("auto.offset.reset", "earliest");
        //properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        kafkaConsumer = new KafkaConsumer<>(properties);
    }

    @Override
    public List<Order> queueReceiveMsg(String topic) {
        kafkaConsumer.subscribe(Arrays.asList(topic));
        List<Order> orders = new ArrayList<>();
        //while (true) {
            //拉取数据
            ConsumerRecords poll = kafkaConsumer.poll(1000);
            poll.forEach(o -> {
                ConsumerRecord<String, String> record = (ConsumerRecord) o;
                Order msg = JSON.parseObject(record.value(), Order.class);
                orders.add(msg);
                System.out.println("order = "+msg);
            });
            kafkaConsumer.commitAsync();
        //}
        return orders;
    }

    @Override
    public void closeConsumer() {
        if(kafkaConsumer!=null){
            kafkaConsumer.close();
        }

    }
}
