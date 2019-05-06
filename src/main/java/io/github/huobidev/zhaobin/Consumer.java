package io.github.huobidev.zhaobin;

public interface Consumer {
  void consumer(String group, String... topic);
}