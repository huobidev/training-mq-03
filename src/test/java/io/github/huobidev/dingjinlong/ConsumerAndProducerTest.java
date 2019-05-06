package io.github.huobidev.dingjinlong;

import com.alibaba.fastjson.JSON;
import io.github.huobidev.Order;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class ConsumerAndProducerTest {


	@Test
	public void consumer() throws IOException {
		Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
		consumerThread.start();
		System.in.read();
	}

	@Test
	public void producer() throws InterruptedException {
		Producer producerThread = new Producer(KafkaProperties.TOPIC);
		long id = 0;
		long its = 0;
		String symbol = "btceth";
		Double price = new Random().nextDouble();

		while (true) {
			Order order = new Order();
			order.setId(id++);
			order.setTs(its++);
			order.setPrice(price);
			order.setSymbol(symbol);
			producerThread.sendData(JSON.toJSONString(order));
			Thread.sleep(1000);
		}

	}


}
