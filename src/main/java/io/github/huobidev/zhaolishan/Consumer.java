package io.github.huobidev.zhaolishan;

import java.util.List;

@FunctionalInterface
public interface Consumer<T> {
    List<T> consume();
}
