package io.github.huobidev.qusifan;

public interface Producer<T> {
  void publish(T t);
}
