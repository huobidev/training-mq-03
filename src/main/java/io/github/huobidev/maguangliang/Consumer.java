package io.github.huobidev.maguangliang;

import java.util.List;

import io.github.huobidev.Order;

public interface Consumer {

    void init();

    List<Order> receive(String topic, Long offset);

    void close();
}
