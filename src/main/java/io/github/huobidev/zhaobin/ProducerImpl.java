package io.github.huobidev.zhaobin;

import io.github.huobidev.Order;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import javax.annotation.PostConstruct;

import kafka.common.KafkaException;

public class ProducerImpl implements Producer {

  private KafkaProducer producer;

  @Override
  public void sendMessages(String topic, Order value) {
    try {
      producer.beginTransaction();
      ProducerRecord record = new ProducerRecord(topic, value);

      producer.send(record, (metadata, exception) -> {
        if (exception != null) {
          producer.abortTransaction();
          throw new KafkaException(exception.getMessage() + ", data: " + record);
        }
      });
      producer.commitTransaction();
    } catch (Throwable e) {
      producer.abortTransaction();
    }
  }

  @PostConstruct
  private void init() {
    if (producer != null) {
      return;
    }

    Properties properties = new Properties();
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    properties.put("bootstrap.servers", "127.0.0.1:8080");
    properties.put("batch.size", "1024");
    properties.put("linger.ms", "1");
    properties.put("enable.idempotence", "true");
    properties.put("retries", "3");
    properties.put("buffer.memory", "45454545");
    properties.put("max.in.flight.requests.per.connection", 1);
    properties.put("acks", "all");
    properties.put("transaction.id", "zhaobinkafka");
    this.producer = new KafkaProducer(properties);
  }
}