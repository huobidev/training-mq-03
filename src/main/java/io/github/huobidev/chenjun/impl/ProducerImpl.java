package io.github.huobidev.chenjun.impl;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import io.github.huobidev.Order;
import io.github.huobidev.chenjun.Producer;
import kafka.common.KafkaException;


public class ProducerImpl implements Producer {

    private KafkaProducer<String, String> kafkaProducer;

    @Override
    public void init(Properties props) {
        kafkaProducer = new KafkaProducer<>(props);
        kafkaProducer.initTransactions();
    }


    @Override
    public void sendMessage(String topic, Order order) {
        if (order == null) {
            return;
        }
        try {
            kafkaProducer.beginTransaction();
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, order.getId().toString(), JSON.toJSONString(order));
            kafkaProducer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    kafkaProducer.abortTransaction();
                    throw new KafkaException(exception.getMessage() + " , data: " + record);
                }
            });
            kafkaProducer.commitTransaction();
        } catch (Throwable e) {
            kafkaProducer.abortTransaction();
        }
    }


}