package io.github.huobidev.jiaruifeng.service;

import com.alibaba.fastjson.JSONObject;
import io.github.huobidev.Order;
import io.github.huobidev.jiaruifeng.OrderProducer;
import io.github.huobidev.jiaruifeng.config.DemoConfig;
import kafka.common.KafkaException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Service
public class ProducerImpl implements OrderProducer {
	Producer<String, String> kafkaProducer;

	@PostConstruct
	public void init() {
		Properties props = new Properties();
		props.put("bootstrap.servers", DemoConfig.BOOTSTRAP_SERVERS);

		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// max.in.flight.requests.per.connection限制客户端在单个连接上能够发送的未响应请求的个数。
		// 设置此值是1表示kafka broker在响应请求之前client不能再向同一个broker发送请求。
		// 注意：设置此参数是保证了有序
		props.put("max.in.flight.requests.per.connection", 1);

		//所有follower都响应了才认为消息提交成功，最大的降低消息丢失的可能性
		props.put("acks", "all");
		/**
		 *	When set enable.idempotence to 'true', the producer will ensure that exactly one copy of each message is written in the stream.
		 *	If 'false', producer retries due to broker failures, etc., may write duplicates of the retried message in the stream.
		 *	Note that enabling idempotence requires max.in.flight.requests.per.connection to be less than or equal to 5, retries to be greater than 0 and acks must be 'all'.
		 *	If these values are not explicitly set by the user, suitable values will be chosen.If incompatible values are set, a ConfigException will be thrown.
		 */
		props.put("enable.idempotence", "true");
//		props.put("transaction.id","xxxx");

		kafkaProducer = new KafkaProducer(props);
	}

	public ProducerRecord produce(Order order) {
//		try {
//			kafkaProducer.beginTransaction();
			ProducerRecord producerRecord = new ProducerRecord(DemoConfig.TOPIC_EOSBTC, order.getId().toString(),
					JSONObject.toJSONString(order));
			kafkaProducer.send(producerRecord);
			kafkaProducer.flush();
//			kafkaProducer.commitTransaction();
			return producerRecord;
//		} catch (Throwable e) {
//			kafkaProducer.abortTransaction();
//		}
//		return null;
	}
}
