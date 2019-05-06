package io.github.huobidev.zhangwentong;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import java.util.Properties;
import kafka.common.KafkaException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class OrderProducer implements Producer<Order> {

    @Override
    public void producer(Order order) {
        Properties properties = KafkaProperty.PRODUCER;
        KafkaProducer kafkaProducer = new KafkaProducer<>(properties);

        try {
            kafkaProducer.initTransactions();
            kafkaProducer.beginTransaction();
            ProducerRecord<String, String> record = new ProducerRecord<>(order.getSymbol(), order.getId().toString(), JSON.toJSONString(order));
            kafkaProducer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    kafkaProducer.abortTransaction();
                    throw new KafkaException(exception.getMessage() + ", data: " + record);
                }
            });
            kafkaProducer.commitTransaction();
            kafkaProducer.flush();
        } catch (Exception e) {
            kafkaProducer.abortTransaction();
        }
    }


}
