package io.github.huobidev.liyuting;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

  private static final ConcurrentHashMap<String, Long> offsetMap = new ConcurrentHashMap<>();

  private AtomicInteger init = new AtomicInteger(1);

  static {
    //模拟从db获取offset值
    offsetMap.put("seq_topic", 1000L);
  }

  //多消费则用个@KafkaHandler处理 https://github.com/spring-projects/spring-kafka/blob/master/samples/sample-02/src/main/java/com/example/MultiMethods.java
  @KafkaListener(id = "group-1", topics = "${spring.kafka.topic}")
  public void listen(
      ConsumerRecord record,
      Acknowledgment acknowledgment,
      org.apache.kafka.clients.consumer.Consumer consumer
  ) {
    try {
      String topic = record.topic();
      Long offset = record.offset();
      int partition = record.partition();

      //初始化从offest位置开始拉去数据,目前临时用map,应持久化到db
      Long seekOffset = offsetMap.get(topic);
      if (init.getAndIncrement() == 1 && null != seekOffset && seekOffset > 0) {
        consumer.seek(new TopicPartition(topic, partition), seekOffset);
        log.info("seek offset:{}", seekOffset);
      }

      //处理相关业务，管理offset，业务数据去重
      log.info("topic:{},Received data:{},partition:{},offset:{}", record.topic(), record.value(), partition, offset);
      offsetMap.put(topic, offset);
      acknowledgment.acknowledge();
    } catch (Exception e) {
      log.error("listen msg err", e);
    }
  }

}
