package io.github.huobidev.chenjun;

import java.util.Properties;

import io.github.huobidev.Order;

/**
 * send
 */
public interface Producer {

    void init(Properties props);

    void sendMessage(String topic, Order order);
}