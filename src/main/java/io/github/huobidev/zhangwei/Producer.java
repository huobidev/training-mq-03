package io.github.huobidev.zhangwei;

import io.github.huobidev.Order;

public interface Producer {
    void produce(String topic, Order order);
}
