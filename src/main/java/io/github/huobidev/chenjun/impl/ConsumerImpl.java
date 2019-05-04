package io.github.huobidev.chenjun.impl;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.github.huobidev.Order;
import io.github.huobidev.chenjun.Consumer;
import io.github.huobidev.chenjun.ConsumerHandler;

/**
 *
 */
public class ConsumerImpl implements Consumer {

    private KafkaConsumer kafkaConsumer;



    public ConsumerImpl() {
        Properties props = new Properties();
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("bootstrap.servers", "localhost:9092");

        props.setProperty("group.id", "myGroup");
        //自动提交offset
        props.put("enable.auto.commit", "true");

        kafkaConsumer = new KafkaConsumer(props);

    }

    @Override
    public void consumer(List<String> topics,ConsumerHandler consumerHandler,ConsumerRebalanceListener consumerRebalanceListener){
        if(topics == null || topics.isEmpty()){
            return;
        }

        if(consumerHandler == null) {
            return;
        }

        if(consumerRebalanceListener != null){
            kafkaConsumer.subscribe(topics, consumerRebalanceListener);
        }else{
            kafkaConsumer.subscribe(topics);
        }

        while (true) { //拉取数据
            ConsumerRecords poll = kafkaConsumer.poll(Duration.ofMillis(100).toMillis());
            poll.forEach(o -> {
                ConsumerRecord<String, String> record = (ConsumerRecord) o;
                consumerHandler.handle(record,JSON.parseObject(record.value(), Order.class));
            });
        }
    }
}