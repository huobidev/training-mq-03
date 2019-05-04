package io.github.huobidev.chenjun;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;

import java.util.Arrays;
import java.util.List;

/**
 * consumer
 */
public interface Consumer {

    void consumer(List<String> topics,ConsumerHandler consumerHandler,ConsumerRebalanceListener consumerRebalanceListener);
}