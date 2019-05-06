package io.github.huobidev.chenyuehui;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

/**
 * Version: v1.0
 * Description: TODO
 */
public class ConsumerImpl implements Consumer {

	private Properties properties;
	private KafkaConsumer<String, String> consumer;
	private final String topic = "order-test1";
	private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
	private Set<String> orderSet = new HashSet<>();
	private volatile boolean flag = true;

	public ConsumerImpl() {
		properties = new Properties();
		properties.put("enable.auto.commit", false);
		properties.put("isolation.level", "read_committed");
		properties.put("auto.offset.reset", "latest");
		properties.put("group.id", "lyle-1");
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer(properties);

	}

	@Override
	public void consumerOrder() {

		consumer.subscribe(Collections.singletonList(topic));

		try {
			while (true) { //拉取数据
				ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(1));

				for (ConsumerRecord o : poll) {
					ConsumerRecord<String, String> record = (ConsumerRecord) o;
					Order order = JSON.parseObject(record.value(), Order.class);
					System.out.println(" order = " + order);
					deduplicationOrder(order);
					currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
						new OffsetAndMetadata(record.offset() + 1, "no matadata"));
					consumer.commitAsync(currentOffsets, new OffsetCommitCallback() {
						@Override
						public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
							if (exception != null) {
								exception.printStackTrace();
							}
						}
					});
				}

			}
		} catch (CommitFailedException e) {
			e.printStackTrace();
		} finally {
			try {
				consumer.commitSync(currentOffsets);
			} catch (Exception e) {
				consumer.close();
			}
		}
	}

	@Override
	public void closeConsumer() {
		if (this.flag) {
			this.flag = false;
		}
		consumer.close();
	}

	private void deduplicationOrder(Order order) {
		orderSet.add(order.getId().toString());
	}

}
