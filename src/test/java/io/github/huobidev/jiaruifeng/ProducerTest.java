package io.github.huobidev.jiaruifeng;


import io.github.huobidev.Order;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(classes = {ServiceScanConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProducerTest {
	@Autowired
	private OrderProducer orderProducer;

	@Test
	public void kafkaTest(){
		String symbol="eosbtc";
		ProducerRecord producerRecordFirst=null;
		ProducerRecord producerRecordLast=null;
		long times=10000l;
		for(int i=0;i<times;i++){

			// 构建数据
			Order order=Order.builder().id((long)i).price(1d).ts(System.currentTimeMillis()).symbol(symbol).build();
			if(i==0){
				// 发送消息
				producerRecordFirst=orderProducer.produce(order);
			}else if(i==times-1){
				producerRecordLast=orderProducer.produce(order);
			}else{
				orderProducer.produce(order);
			}
		}
		System.out.println(producerRecordFirst.toString());
		System.out.println(producerRecordLast.toString());
//		producerRecordFirst.
	}

}
