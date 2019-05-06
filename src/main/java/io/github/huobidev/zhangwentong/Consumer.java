package io.github.huobidev.zhangwentong;

public interface Consumer {

    void ConsumerFromOffset(String topic, Long offset);

    void Consumer(String topic);
}

