package io.github.huobidev.chenjun.impl;

import org.junit.Test;

import java.util.List;

import io.github.huobidev.Order;
import io.github.huobidev.chenjun.Consumer;
import io.github.huobidev.chenjun.config.KafkaConfig;

public class ConsumerImplTest {


    @Test
    public void consumer() throws InterruptedException {
        Consumer consumer = new ConsumerImpl();
        consumer.init(KafkaConfig.CONSUMER_CONFIG, "order", 0L);

        while (true) {
            List<Order> order = consumer.consume();
            Thread.sleep(1000L);
            order.forEach(System.err::println);
        }
    }
}