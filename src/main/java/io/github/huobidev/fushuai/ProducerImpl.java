package io.github.huobidev.fushuai;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.Future;

public class ProducerImpl<Order> implements Producer{


    private KafkaProducer kafkaProducer;

    void createProducer(){
        Properties pro = new Properties();
        pro.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pro.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pro.put("bootstrap.servers", "127.0.0.1:9092");
        pro.put("max.in.flight.requests.per.connection", "1");
        pro.put("batch.size", "10240");
        pro.put("linger.ms", "1");
        pro.put("enable.idempotence", "true");
        pro.put("retries", "3");
        pro.put("buffer.memory", "33554432");
        pro.put("acks","all");
        pro.put("transaction.id","test_queueSendMsg");
        kafkaProducer = new KafkaProducer(pro);
    }


    @Override
    public void queueSendMsg(String topic, Object order) {
        ProducerRecord record = new ProducerRecord(topic,JSON.toJSONString(order));
        Future future = kafkaProducer.send(record);
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception == null){
                System.out.println("record = " + record);
            }
        });
        //异步发送⽅方法2
        kafkaProducer.send(record);

    }

    @Override
    public void closePrducer() {
        if(kafkaProducer!=null){
            kafkaProducer.close();
        }

    }


}
