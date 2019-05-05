package io.github.huobidev.yangchuang;

import io.github.huobidev.Order;
import kafka.common.KafkaException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerImpl implements Producer {

    @Override
    public void send(Order order) {

        KafkaProducer kafkaProducer = KafkaFactory.buildProducer();
        try {

            kafkaProducer.beginTransaction();
            ProducerRecord<String, Order> record = new ProducerRecord<>(KafkaFactory.topicName, KafkaFactory.key, order);

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

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            Producer producer = new ProducerImpl();
            Order order = new Order();
            order.setId(1L + i);
            order.setTs(1000L + i);
            order.setSymbol("btcusdt");
            order.setPrice(4444.1D - i * 0.1);
            producer.send(order);
            System.err.println(order);
            Thread.sleep(100L);
        }
    }
}



