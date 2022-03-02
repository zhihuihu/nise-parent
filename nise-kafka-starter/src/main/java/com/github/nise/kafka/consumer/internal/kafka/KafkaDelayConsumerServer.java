package com.github.nise.kafka.consumer.internal.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 延迟消费者
 *
 * @author JiangZhe  2/20/22.
 */
@Slf4j
public class KafkaDelayConsumerServer implements Runnable {

    private KafkaConsumer<String, byte[]> delayConsumer;
    private KafkaProducer<String, byte[]> normalProducer;

    public KafkaDelayConsumerServer(KafkaProducer<String, byte[]> normalProducer, KafkaConsumer<String, byte[]> delayConsumer) {
        this.normalProducer = normalProducer;
        this.delayConsumer = delayConsumer;
    }

    @Override
    public void run() {
        log.info("开始消息延迟线程，线程：" + Thread.currentThread().getName());
        while (true) {
            if (delayConsumer != null) {
                Map<TopicPartition, OffsetAndMetadata> currentOffsets = new ConcurrentHashMap<>();
                ConsumerRecords<String, byte[]> records = delayConsumer.poll(Duration.ofMillis(10000));
                for (ConsumerRecord<String, byte[]> record : records) {
                    long timestamp = record.timestamp();
                    TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
                    if (timestamp < System.currentTimeMillis()) {
                        String value = String.valueOf(record.value());
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = null;
                        try {
                            jsonNode = objectMapper.readTree(value);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        JsonNode jsonNodeTopic = jsonNode.get("topic");

                        String appTopic = null, appKey = null, appValue = null;
                        if (jsonNodeTopic != null) {
                            appTopic = jsonNodeTopic.asText();
                        }
                        if (appTopic == null) {
                            continue;
                        }
                        JsonNode jsonNodeKey = jsonNode.get("key");
                        if (jsonNodeKey != null) {
                            appKey = jsonNode.asText();
                        }

                        JsonNode jsonNodeValue = jsonNode.get("value");
                        if (jsonNodeValue != null) {
                            appValue = jsonNodeValue.asText();
                        }

                        //生产正常消息
                        ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<>(appTopic, appKey, appValue.getBytes(StandardCharsets.UTF_8));
                        try {
                            normalProducer.send(producerRecord).get();
                            //提交
                            currentOffsets.put(topicPartition, new OffsetAndMetadata(record.offset() + 1, "delay commit"));
                            delayConsumer.commitSync(currentOffsets);
                        } catch (Exception e) {
                            delayConsumer.seek(topicPartition, record.offset());
                        }
                    }
                    else {
                        delayConsumer.seek(topicPartition, record.offset());
                    }

                }

            }
        }
    }
}
