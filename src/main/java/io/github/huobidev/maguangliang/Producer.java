package io.github.huobidev.maguangliang;

import io.github.huobidev.Order;

public interface Producer {

    void init();

    void send(String topic, Order order);

    void close();
}
