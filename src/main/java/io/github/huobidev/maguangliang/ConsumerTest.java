package io.github.huobidev.maguangliang;

import com.alibaba.fastjson.JSON;

import java.util.List;

import io.github.huobidev.Order;
import io.github.huobidev.maguangliang.impl.ConsumerImpl;
import io.github.huobidev.maguangliang.impl.ProducerImpl;

public class ConsumerTest {

    public static void main(String[] args) {
        ConsumerImpl consumer = new ConsumerImpl();
        consumer.init();
        String topic = "btcusdt";
        while (true) {
            List<Order> orderList = consumer.receive(topic, null);
            for (Order order : orderList) {
                System.out.println("==========" + JSON.toJSONString(order));
            }
        }
    }

}

class ProducerTest {
    public static void main(String[] args) {
        ProducerImpl producer = new ProducerImpl();
        producer.init();
        String topic = "btcusdt";

        for (int i = 0; i < 200; i++) {
            Order order = new Order();
            order.setId((long) i);
            order.setTs(1L);
            order.setSymbol("btcusdt");
            order.setPrice(0.0D);

            producer.send(topic, order);
        }
    }

    public static void send() {



    }
}

