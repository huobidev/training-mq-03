package io.github.huobidev.chenjun.impl;

import org.junit.Test;

import io.github.huobidev.Order;
import io.github.huobidev.chenjun.Producer;
import io.github.huobidev.chenjun.config.KafkaConfig;

public class ProducerImplTest {

    @Test
    public void sendMessage() {
        Producer producer = new ProducerImpl();
        producer.init(KafkaConfig.PRODUCER_CONFIG);

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setId(i + 1L);
            order.setTs(1000L + i);
            order.setSymbol("btcusdt");
            order.setPrice(10000d);
            producer.sendMessage("order", order);
        }
    }
}