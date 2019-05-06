package io.github.huobidev.kongdeyuan;

public interface Consumer {

    void receive();

    void receive(Long offset);

}
