package io.github.huobidev.chenyuehui;

import io.github.huobidev.Order;

public interface Producer {

	void sendOrder(Order order);

	void closeProducer();
}
