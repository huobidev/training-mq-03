package io.github.huobidev.kongdeyuan;

import io.github.huobidev.Order;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class KafkaTest {

    private Consumer consumer;
    private Producer producer;


    @Before
    public void before() {
        consumer = new ConsumerImpl();
        producer = new ProducerImpl();
    }

    @Test
    public void testConsume1() {
        consumer.receive();
    }


    @Test
    public void testProduce() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setTs(System.currentTimeMillis());
        order1.setPrice(10.0);
        order1.setSymbol("eosusdt");
        List<Order> orders = new ArrayList<>();
        orders.add(order1);

        for (Order order : orders) {
            producer.send(order);
        }
    }

    @Test
    public void testConsume2() {
        consumer.receive(2L);
    }

}
