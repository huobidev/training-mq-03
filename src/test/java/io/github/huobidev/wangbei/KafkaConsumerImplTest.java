package io.github.huobidev.wangbei;

import io.github.huobidev.qinjinwei.Consumer;
import io.github.huobidev.wangbei.impl.KafkaConsumerImpl;
import org.junit.Before;
import org.junit.Test;

public class KafkaConsumerImplTest {

  Consumer consumer = new KafkaConsumerImpl();

  @Before
  public void before() {
  }

  @Test
  public void testConsume() {
    consumer.consume();
  }


  @Test
  public void testConsumeWithOffset() {
    consumer.consume(3L);
  }

}
