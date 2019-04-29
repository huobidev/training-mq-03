package io.github.huobidev.zhaolishan.example;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import io.github.huobidev.zhaolishan.KafkaClientConfig;
import io.github.huobidev.zhaolishan.OrderConsumer;

import java.util.List;

public class OrderConsumerEx {

    private static OrderConsumer consumer = new OrderConsumer(KafkaClientConfig.getConsumerProps(), "t_order");

    public static void main(String[] args) {
        while (true) {
            List<Order> orders = consumer.consume();
            for (Order order : orders) {
                System.out.println(JSON.toJSONString(order));
            }
        }
    }
}
