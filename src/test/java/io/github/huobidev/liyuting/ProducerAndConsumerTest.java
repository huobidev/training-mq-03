package io.github.huobidev.liyuting;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class ProducerAndConsumerTest {

  private static String SENDER_TOPIC = "test-1";

  @ClassRule
  public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(1, false, SENDER_TOPIC);


  @Test
  public void testSendAndRecive() {
    EmbeddedKafkaBroker embeddedKafkaBroker = embeddedKafkaRule.getEmbeddedKafka();
    Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "false", embeddedKafkaBroker);
    DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<Integer, String>(consumerProps);
    ContainerProperties containerProperties = new ContainerProperties(SENDER_TOPIC);
    KafkaMessageListenerContainer<Integer, String> container = new KafkaMessageListenerContainer<>(cf, containerProperties);
    final BlockingQueue<ConsumerRecord<Integer, String>> records = new LinkedBlockingQueue<>();
    container.setupMessageListener(new MessageListener<Integer, String>() {
      @Override
      public void onMessage(ConsumerRecord<Integer, String> record) {
        log.info("onMessage record:{}", record);
        records.add(record);
      }
    });

    container.setBeanName("templateTests");
    container.start();
    ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());

    Map<String, Object> senderProps = KafkaTestUtils.senderProps(embeddedKafkaBroker.getBrokersAsString());
    ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<Integer, String>(senderProps);

    KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
    template.setDefaultTopic(SENDER_TOPIC);
    template.sendDefault("foo");

    try {
      ConsumerRecord consumerRecord = records.poll(10, TimeUnit.SECONDS);
      assertTrue(consumerRecord.value().equals("foo"));
    } catch (InterruptedException e) {
      log.error("records poll err", e);
      Thread.currentThread().interrupt();
    }

  }

}
