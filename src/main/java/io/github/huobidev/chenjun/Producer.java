package io.github.huobidev.chenjun;

import io.github.huobidev.Order;

/**
 * send
 */
public interface Producer {

  void sendMessage(String topic,Order order);
}