package io.github.huobidev.wangxiaoliang.impl;

import io.github.huobidev.Order;
import io.github.huobidev.wangxiaoliang.Producer;
import java.util.Properties;
import kafka.common.KafkaException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class ProducerImpl implements Producer {

    private KafkaProducer kafkaProducer;

    private KafkaProducer buildProducer() {
        if (kafkaProducer == null) {
            Properties producerPro = new Properties();
            producerPro.put("bootstrap.servers", "127.0.0.1:9092");
            producerPro.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producerPro.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producerPro.put("max.in.flight.requests.per.connection", "1");
            producerPro.put("ack", "all");
            producerPro.put("enable.idempotence", "true");
            //producerPro.put("retries", 3);
            producerPro.put("transactional.id", "send.order.transaction.id");
            kafkaProducer = new KafkaProducer(producerPro);
            kafkaProducer.initTransactions();
        }
        return kafkaProducer;
    }

    @Override
    public void send(Order order) {

        kafkaProducer = this.buildProducer();
        try {

            kafkaProducer.beginTransaction();
            ProducerRecord<String, Order> record = new ProducerRecord("topic-name-test", order.getId(), order);

            kafkaProducer.send(record, (metadata, e) -> {
                if (e != null) {
                    kafkaProducer.abortTransaction();
                    throw new KafkaException(e.getMessage() + ", data: " + record);
                }
            });

            kafkaProducer.commitTransaction();
            kafkaProducer.flush();
        } catch (Exception e) {
            kafkaProducer.abortTransaction();
        }
    }
}