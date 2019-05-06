package io.github.huobidev.chenlike;

import io.github.huobidev.Order;

public interface Producer {
	void sendOrder(Order order);

	void closeProducer();
}
