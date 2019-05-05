package io.github.huobidev.zhangwei;

import com.alibaba.fastjson.JSON;

import java.util.List;

import io.github.huobidev.Order;

public class ConsumerTest {
    private static ConsumerImpl consumer = new ConsumerImpl();

    public static void main(String[] args) {
        String topic = "btcusdt";
        // todo 从数据库中取
        Long offset = 0l;
        while (true) {
            List<Order> orders = consumer.consume(topic, offset);
            for (Order order : orders) {
                System.out.println(JSON.toJSONString(order));
            }
        }
    }
}
