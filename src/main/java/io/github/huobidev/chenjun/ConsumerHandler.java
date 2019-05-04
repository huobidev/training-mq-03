package io.github.huobidev.chenjun;

import io.github.huobidev.Order;

/**
 * consumer handler
 */
@FunctionalInterface
public interface ConsumerHandler {

    void handle(Object metadata, Order order);
}