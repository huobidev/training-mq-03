package io.github.huobidev.qusifan;


import java.util.Properties;

import io.github.huobidev.qusifan.entity.BaseEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

//thread not safe
public class KfkConsumer<T extends BaseEntity> implements AutoCloseable {

  private final KafkaConsumer<String, String> kafkaConsumer;

  private final Consumer<T> consumer;

  private volatile boolean running = true;

  public KfkConsumer(Consumer<T> consumer) {
    this.consumer = consumer;
    Properties properties = getProperties();
    kafkaConsumer = new KafkaConsumer<>(properties);
    poll();
  }

  private Properties getProperties() {
    Properties properties = new Properties();
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    properties.setProperty("bootstrap.servers", "localhost:9092");
    properties.setProperty("group.id", "qusifan");
    properties.setProperty("enable.auto.commit", "false");
    return properties;
  }

  @Override
  public void close() {
    running = false;
  }

  private void poll() {
    while (running) {
      processRecord(kafkaConsumer.poll(3000L));
      kafkaConsumer.commitSync();
    }
  }

  private void processRecord(ConsumerRecords<String, String> records) {
    for (ConsumerRecord<String, String> record : records) {
      System.out.println("process message success." + record.toString());
//      consumer.consume(t);  need json parser
    }
  }

}
