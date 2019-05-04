package io.github.huobidev.jiaruifeng.service;

import io.github.huobidev.jiaruifeng.OrderConsumer;
import io.github.huobidev.jiaruifeng.config.DemoConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;


@Service
public class ConsumerImpl implements OrderConsumer {
	KafkaConsumer<String, String> consumer;

	@PostConstruct
	public void init() {
		Properties props = new Properties();
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("enable.auto.commit", "true");
		props.setProperty("auto.commit.interval.ms", "1000");
		props.setProperty("bootstrap.servers", DemoConfig.BOOTSTRAP_SERVERS);
		props.setProperty("group.id", "test");

		consumer = new KafkaConsumer(props);
		consumer.subscribe(Arrays.asList(DemoConfig.TOPIC_EOSBTC), new ConsumerRebalanceListener() {
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
				commitOffsetToDB();
			}

			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
				partitions.forEach(topicPartition -> consumer.seek(topicPartition, getOffsetFromDB(topicPartition)));
			}
		});

	}

	private long getOffsetFromDB(TopicPartition topicPartition) {
		return 29469;

	}

	private void commitOffsetToDB() {

	}

	public void consume() {
		while (true) {
			ConsumerRecords records = consumer.poll(1l);
			records.forEach(o -> {
				ConsumerRecord<String, String> record = (ConsumerRecord) o;
				processRecord(record);
				saveRecordAndOffsetInDB(record, record.offset());

			});
		}
	}
	public void consumeOnce() {
		ConsumerRecords records = consumer.poll(1000l);
		records.forEach(o -> {
			ConsumerRecord<String, String> record = (ConsumerRecord) o;
			processRecord(record);
			saveRecordAndOffsetInDB(record, record.offset());

		});
	}
	/**
	 * 保存消息和消息的偏移位置
	 * @param record
	 * @param offset
	 */
	private void saveRecordAndOffsetInDB(ConsumerRecord<String, String> record, long offset) {

	}

	/**
	 * 对消息进行消费
	 * @param record
	 */
	private void processRecord(ConsumerRecord<String, String> record) {
		System.out.printf(">>>>offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
				record.value());

	}


}
