package io.github.huobidev.zhaobin;

import io.github.huobidev.Order;

public interface Producer {
  void sendMessages(String topic, Order value);
}
