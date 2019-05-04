package io.github.huobidev.liyuting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan("io.github.huobidev")
@SpringBootApplication
public class KafkaDemoApplication {

  @Value("${spring.kafka.consumer.enable-auto-commit}")
  private boolean autoCommit;

  public static void main(String[] args) {
    SpringApplication.run(KafkaDemoApplication.class, args);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
      ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
      ConsumerFactory<Object, Object> kafkaConsumerFactory,
      KafkaTemplate<Object, Object> template) {
    ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    configurer.configure(factory, kafkaConsumerFactory);
    factory.setErrorHandler(new SeekToCurrentErrorHandler(
        new DeadLetterPublishingRecoverer(template), 3)); // dead-letter after 3 tries
    if (!autoCommit) {
      factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
    }
    return factory;
  }

}
