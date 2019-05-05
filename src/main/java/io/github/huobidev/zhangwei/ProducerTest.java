package io.github.huobidev.zhangwei;

import java.util.Random;

import io.github.huobidev.Order;

public class ProducerTest {
    public static void main(String[] args) {
        ProducerImpl producer = new ProducerImpl();
        String topic = "btcusdt";

        for (int i = 0; i < 100000; i++) {
            Order order = new Order();
            order.setId((long) i);
            order.setTs(System.currentTimeMillis());
            order.setSymbol("btcusdt");
            Random random = new Random();
            order.setPrice(random.nextDouble());

            producer.produce(topic, order);
        }
    }
}
