package io.github.huobidev.zhangwei;

import java.util.List;

import io.github.huobidev.Order;

public interface Consumer {
    List<Order> consume(String topic,Long offset);
}
