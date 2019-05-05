package io.github.huobidev.wangbingzhen;

import io.github.huobidev.Order;

/**
 * Created by prowww on 2019/5/5.
 */
public class KafTest {
    public static void main(String[] args) {
        ProducerImpl producer = new ProducerImpl();
        ConsumerImpl consumer = new ConsumerImpl();
        OrderSymbol order = new OrderSymbol();
        order.setId(1L);
        order.setPrice(Double.valueOf("35000"));
        order.setSymbol("btcusdt");
        producer.sendMsg("market", order);
        consumer.receiveMsg("market");
    }
}
