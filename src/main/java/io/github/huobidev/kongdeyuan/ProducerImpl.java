package io.github.huobidev.kongdeyuan;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerImpl implements Producer {

    private KafkaProducer kafkaProducer;

    public ProducerImpl() {
        Properties props = new Properties();
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("linger.ms", "1");
        props.setProperty("enable.idempotence", "true");
        props.setProperty("max.in.flight.requests.per.connection", "1");

        kafkaProducer = new KafkaProducer(props);
    }

    @Override
    public void send(Order order) {
        ProducerRecord record = new ProducerRecord("topic",
                order.getId().toString(), JSON.toJSONString(order));

        kafkaProducer.send(record);
        kafkaProducer.flush();
    }

}
