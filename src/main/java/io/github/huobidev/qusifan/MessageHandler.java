package io.github.huobidev.qusifan;

public interface MessageHandler<T> {
  void handle(T t);
}
