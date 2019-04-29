package io.github.huobidev.zhaolishan;

@FunctionalInterface
public interface Producer<T> {
    void produce(T t);
}
