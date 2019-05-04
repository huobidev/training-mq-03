package io.github.huobidev.chenjun;

import java.util.List;
import java.util.Properties;

import io.github.huobidev.Order;

/**
 * consumer
 */
public interface Consumer {

    void init(Properties props, String topic, Long offset);

    List<Order> consume();
}