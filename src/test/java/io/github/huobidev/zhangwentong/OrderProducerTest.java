package io.github.huobidev.zhangwentong;

import static org.junit.Assert.*;

import io.github.huobidev.Order;
import org.junit.Test;

public class OrderProducerTest {

    @Test
    public void producer() {
        OrderProducer producer = new OrderProducer();
        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setId(i + 1L);
            order.setTs(1000L + i);
            order.setSymbol("btcusdt");
            order.setPrice(10000d);
            producer.producer(order);
        }
    }
}