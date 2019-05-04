package io.github.huobidev.wangbei.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.qinjinwei.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class KafkaConsumerImpl implements Consumer {

  private Set<String> keys = new HashSet<>();

  private KafkaConsumer consumer;

  public KafkaConsumerImpl() {
    Properties props = new Properties();
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("group.id", "groupName");

    props.put("enable.auto.commit","true");
    props.put("auto.commit.interval.ms", "5000");

    consumer = new KafkaConsumer(props);
    consumer.assign(Arrays.asList(new TopicPartition("topic", 0)));
//    consumer.subscribe(Arrays.asList("topic"));
  }

  @Override
  public void consume() {
    this.consume(0L);
  }

  @Override
  public void consume(long offset) {
    consumer.seek(new TopicPartition("topic", 0), offset);
    while (true){
      ConsumerRecords poll = consumer.poll(1000L);

      poll.forEach(
          o -> {
            ConsumerRecord<String, String> record = ((ConsumerRecord<String, String>) o);
            this.process(record);
          });
    }
  }

  private void process(ConsumerRecord<String, String> record) {
    if (keys.contains(record.key())) {
      System.out.println("ignore:" + recordToString(record));
      return;
    }
    System.out.println(recordToString(record));

    keys.add(record.key());
  }

  private String recordToString(ConsumerRecord<String, String> record) {
    return new StringBuffer("{offset=").append(record.offset())
        .append(", partition=").append(record.partition())
        .append(", key=").append(record.key())
        .append(", value=").append(record.value())
        .append("}").toString();
  }

}
