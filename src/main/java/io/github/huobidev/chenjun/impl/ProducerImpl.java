package io.github.huobidev.chenjun.impl;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import io.github.huobidev.Order;
import io.github.huobidev.chenjun.Producer;
import kafka.common.KafkaException;

/**
 * kafka发送类
 */
public class ProducerImpl implements Producer {

    private  KafkaProducer kafkaProducer;

    public ProducerImpl(){
        Properties pro = new Properties();
        pro.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pro.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pro.put("bootstrap.servers", "localhost:9092");

        //顺序保证
        pro.put("max.in.flight.requests.per.connection", "1");
        //开启事务
        pro.put("acks","all");
        pro.put("enable.idempotence","true");
        pro.put("transaction.id","order");

        kafkaProducer = new KafkaProducer(pro);
    }

    @Override
    public void sendMessage(String topic,Order order) {
        if(order == null) {
            return;
        }
        try {
            kafkaProducer.beginTransaction();
            ProducerRecord record = new ProducerRecord(topic, order.getId(), JSON.toJSONString(order));
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