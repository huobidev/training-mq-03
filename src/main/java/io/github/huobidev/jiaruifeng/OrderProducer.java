package io.github.huobidev.jiaruifeng;

import io.github.huobidev.Order;
import org.apache.kafka.clients.producer.ProducerRecord;

public interface OrderProducer {

	ProducerRecord produce(Order order);
}
