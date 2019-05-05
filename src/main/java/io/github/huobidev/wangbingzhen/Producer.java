package io.github.huobidev.wangbingzhen;

import io.github.huobidev.Order;

public interface Producer {


    // add your interface method here
    void sendMsg(String topic, OrderSymbol order);
    // and then implement it

}
