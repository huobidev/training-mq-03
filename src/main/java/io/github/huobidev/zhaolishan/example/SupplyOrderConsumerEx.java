package io.github.huobidev.zhaolishan.example;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import io.github.huobidev.zhaolishan.KafkaClientConfig;
import io.github.huobidev.zhaolishan.OrderConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.List;

public class SupplyOrderConsumerEx {

    private static OrderConsumer consumer = new OrderConsumer(KafkaClientConfig.getConsumerProps()
            , "t_order", "g_supply_01", new TopicPartition("t_order", 0), 10);

    public static void main(String[] args) {
        while (true) {
            List<Order> orders = consumer.consume();
            for (Order order : orders) {
                System.out.println(JSON.toJSONString(order));
            }
        }
    }
}
