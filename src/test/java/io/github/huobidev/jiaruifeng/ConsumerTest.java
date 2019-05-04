package io.github.huobidev.jiaruifeng;


import io.github.huobidev.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(classes = {ServiceScanConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConsumerTest {
	@Autowired
	private OrderConsumer orderConsumer;

	@Test
	public void kafkaTest(){
		orderConsumer.consume();
	}

}
