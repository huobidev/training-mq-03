package io.github.huobidev.zhangwentong;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrderConsumerTest {

    @Test
    public void consumerFromOffset() {
        OrderConsumer orderConsumer = new OrderConsumer();
        orderConsumer.ConsumerFromOffset("btcusdt", 1101L);

    }

    @Test
    public void consumer() {
        OrderConsumer orderConsumer = new OrderConsumer();
        orderConsumer.Consumer("btcusdt");
    }
}