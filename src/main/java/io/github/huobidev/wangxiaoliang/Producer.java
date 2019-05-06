package io.github.huobidev.wangxiaoliang;

import io.github.huobidev.Order;

/**
 * @author wangxiaoliang
 * @date 2019-05-06 下午4:32
 * @description:
 */
public interface Producer {
    void send(Order order);
}
