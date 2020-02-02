package com.example.kafkaexample.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerExample {
    public static void main(String ... args) {
        String topicName = "test-topic";

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms",  "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Arrays.asList(topicName));

        System.out.println(String.format("Subscribed to topic %s", topicName));

        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for(ConsumerRecord<String,String> record : records) {
                System.out.println(String.format("Offset = %d, Key = %s, Value = %s",
                        record.offset(), record.key(), record.value()));
            }
        }
    }
}
