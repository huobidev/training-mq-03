package io.github.huobidev.qusifan;


import java.util.Properties;
import java.util.concurrent.ExecutionException;

import io.github.huobidev.qusifan.entity.BaseEntity;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KfkProducer<T extends BaseEntity> implements Producer<T> {

  private final KafkaProducer<String, String> kafkaProducer;

  public KfkProducer() {
    kafkaProducer = new KafkaProducer<>(getKafkaProperties());
  }

  private Properties getKafkaProperties() {
    Properties props = new Properties();
    props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.setProperty("linger.ms", "1");
    props.setProperty("enable.idempotence", "true");
    props.setProperty("max.in.flight.requests.per.connection", "1");
    props.setProperty("bootstrap.servers", "localhost:9092");
    return props;
  }

  @Override
  public void publish(T t) {
    ProducerRecord<String, String> producerRecord = new ProducerRecord<>("testTopic", t.getId().toString(), t.toString());
    kafkaProducer.send(producerRecord, (metadata, ex) -> {
      if (ex == null) {
        System.out.println("send success " + t.getId() + " offset .. " + metadata.offset());
        return;
      }
      System.out.println("send error" + t.getId() + " exception:" + ex.getMessage());
    });
  }

}
