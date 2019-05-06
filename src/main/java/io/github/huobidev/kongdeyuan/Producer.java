package io.github.huobidev.kongdeyuan;

import io.github.huobidev.Order;

public interface Producer {

    void send(Order order);

}
