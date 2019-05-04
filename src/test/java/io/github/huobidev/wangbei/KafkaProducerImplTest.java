package io.github.huobidev.wangbei;

import java.util.ArrayList;
import java.util.List;

import io.github.huobidev.Order;
import io.github.huobidev.qinjinwei.Producer;
import io.github.huobidev.wangbei.impl.KafkaProducerImpl;
import org.junit.Before;
import org.junit.Test;

public class KafkaProducerImplTest {

  Producer producer = new KafkaProducerImpl();

  List<Order> orders;

  @Before
  public void before() {
    Order order1 = new Order();
    order1.setId(1L);
    order1.setTs(System.currentTimeMillis());
    order1.setPrice(100.0);
    order1.setSymbol("btcusdt");

    Order order2 = new Order();
    order2.setId(2L);
    order2.setTs(System.currentTimeMillis());
    order2.setPrice(200.0);
    order2.setSymbol("btcusdt");

    orders = new ArrayList<>();
    orders.add(order1);
    orders.add(order2);
  }

  @Test
  public void testProduce() {
    for (Order order : orders) {
      producer.produce(order);
    }
  }

  @Test
  public void testProduceTwice() {
    for (int i = 0; i < 2; i++) {
      for (Order order : orders) {
        producer.produce(order);
      }
    }
  }
}
