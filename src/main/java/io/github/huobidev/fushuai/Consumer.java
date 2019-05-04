package io.github.huobidev.fushuai;

import java.util.List;

public interface Consumer<T>{

    public List<T> queueReceiveMsg(String topic);

    void closeConsumer();


}
