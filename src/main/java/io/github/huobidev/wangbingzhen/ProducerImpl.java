package io.github.huobidev.wangbingzhen;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 *
 */
public class ProducerImpl implements Producer{


    @Override
    public void sendMsg(String topic, OrderSymbol order) {
        Properties pro = new Properties();
        pro.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pro.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pro.put("bootstrap.servers", "127.0.0.1:9092");
        pro.put("max.in.flight.requests.per.connection", "1");
        pro.put("batch.size", "10240");
        pro.put("linger.ms", "1");
        pro.put("enable.idempotence", "true");
        pro.put("retries", "3");
        pro.put("buffer.memory", "33554432");
        pro.put("acks","all");
        pro.put("transaction.id","MSGQUEUE");
        KafkaProducer kafkaProducer = new KafkaProducer(pro);
        ProducerRecord producerRecord = new ProducerRecord(topic, JSON.toJSONString(order));
        kafkaProducer.send(producerRecord, (metadata, exception) -> {
            if (null == exception) {
                System.out.println(producerRecord);
            }
        });
        kafkaProducer.close();
    }
}
