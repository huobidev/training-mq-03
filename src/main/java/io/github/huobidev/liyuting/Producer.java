package io.github.huobidev.liyuting;

import io.github.huobidev.Order;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {

  @Value("${spring.kafka.topic}")
  private String topic;

  @Autowired
  private KafkaTemplate<Object, Object> template;

  //暂时用作递增id
  private AtomicLong id = new AtomicLong(1);

  @Scheduled(initialDelayString = "${send.delay:10}", fixedRateString = "${send.fixed.rate:30000}")
  public void init() {
    for (int i = 0; i < 100; i++) {
      Order order = new Order();
      order.setId(id.getAndIncrement());
      order.setPrice(i + 100D);
      order.setSymbol("test_" + i);
      order.setTs(System.currentTimeMillis());
      send(order);
    }
  }

  public void send(Order order) {
    template.send(topic, order);
  }

}
