package io.github.huobidev.dingjinlong;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer {
	private final KafkaProducer<Integer, String> producer;
	private final String topic;
	private AtomicInteger messageNo = new AtomicInteger(0);

	public Producer(String topic) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
						KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producer = new KafkaProducer<>(props);
		this.topic = topic;
	}

	public void sendData(String msg) {
		producer.send(new ProducerRecord<>(topic, messageNo.incrementAndGet(), msg));
		producer.flush();
	}
}