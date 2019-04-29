package io.github.huobidev.zhaolishan.example;

import io.github.huobidev.Order;
import io.github.huobidev.zhaolishan.KafkaClientConfig;
import io.github.huobidev.zhaolishan.OrderProducer;

import java.util.concurrent.TimeUnit;

public class OrderProducerEx {

    private static OrderProducer orderKafkaProducer = new OrderProducer(KafkaClientConfig.getProducerProps(), "t_order");

    public static void main(String[] args) throws InterruptedException {
        Order order = new Order();
        order.setId(1L);
        order.setPrice(6300.0);
        order.setSymbol("btcusdt");
        for (int i = 0; i < 100; i++) {
            order.setTs(System.currentTimeMillis());
            TimeUnit.MILLISECONDS.sleep(1000L);
            orderKafkaProducer.produce(order);
        }
    }
}
