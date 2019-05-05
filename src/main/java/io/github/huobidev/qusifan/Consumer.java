package io.github.huobidev.qusifan;

import io.github.huobidev.qusifan.entity.BaseEntity;

public interface Consumer<T extends BaseEntity> {
  void consume(T t);
}
