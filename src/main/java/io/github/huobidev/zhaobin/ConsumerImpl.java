package io.github.huobidev.zhaobin;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

public class ConsumerImpl implements ApplicationListener<ApplicationReadyEvent>, Consumer {

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    consumer("zhaobin", "zhaobinTopic");
  }

  @Override
  public void consumer(String group, String... topics) {
    Properties props = initProperties();
    props.setProperty("group.id", group);
    KafkaConsumer consumer = new KafkaConsumer(props);
    consumer.subscribe(Arrays.asList(topics), new ConsumerRebalanceListener() {
      @Override
      public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        commitOffsetToDB(partitions);
      }

      @Override
      public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        partitions.forEach(topicPartition -> consumer.seek(topicPartition, getOffsetFromDB(topicPartition)));
      }
    });
    while (true) {
      try {
        ConsumerRecords poll = consumer.poll(100);
        poll.forEach(o -> {
          ConsumerRecord<String, String> record = (ConsumerRecord) o;
          processRecord(record);
          saveRecordAndOffsetInDB(record, record.offset());
        });
      } catch (Exception e) {
      }
    }
  }

  private void saveRecordAndOffsetInDB(ConsumerRecord<String, String> record, long offset) {
  }

  private void processRecord(ConsumerRecord<String, String> record) {
  }

  private void commitOffsetToDB(Collection<TopicPartition> partitions) {
  }

  private long getOffsetFromDB(TopicPartition topicPartition) {
    return 0;
  }

  private Properties initProperties() {
    Properties props = new Properties();
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("bootstrap.servers", "127.0.0.1:8080");
    props.put("enable.auto.commit", "true");
    return props;
  }
}