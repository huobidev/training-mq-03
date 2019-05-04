package io.github.huobidev.chenjun.impl;

import io.github.huobidev.Order;
import io.github.huobidev.chenjun.ConsumerHandler;
import lombok.extern.log4j.Log4j;

/**
 *
 */
@Log4j
public class ConsumerHandlerImpl implements ConsumerHandler {

    @Override
    public void handle(Object metadata, Order order) {
        log.info(order);
    }
}