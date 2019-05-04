package io.github.huobidev.fushuai;

public interface Producer<T>{

    /**
     * 发送消息
     * @param topic
     * @param order
     */
    void queueSendMsg(String topic,T order);

    /**
     * 关闭链接
     */
    void closePrducer();


}
