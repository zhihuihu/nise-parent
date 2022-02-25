package com.github.nise.kafka.consumer.internal.kafka;

import com.github.nise.kafka.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.internals.RecordHeaders;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 重试消费者
 *
 * @author JiangZhe  2/20/22.
 */
@Slf4j
public class KafkaRetryConsumerServer implements Runnable {

    private int propertiesRetryCount;
    private KafkaConsumer<String, byte[]> retryConsumer;
    private KafkaProducer<String, byte[]> retryProducer;

    public KafkaRetryConsumerServer(KafkaProducer<String, byte[]> retryProducer, KafkaConsumer<String, byte[]> retryConsumer, int propertiesRetryCount) {
        this.retryProducer = retryProducer;
        this.retryConsumer = retryConsumer;
        this.propertiesRetryCount = propertiesRetryCount;
    }

    @Override
    public void run() {
        log.info("开始消息重试线程");
        while (true) {
            if (retryConsumer != null) {
                Map<TopicPartition, OffsetAndMetadata> currentOffsets = new ConcurrentHashMap<>();
                ConsumerRecords<String, byte[]> records = retryConsumer.poll(10000);
                for (ConsumerRecord<String, byte[]> record : records) {
                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1, "retry commit"));
                    retryConsumer.commitSync(currentOffsets);
                    RecordHeaders recordHeaders = (RecordHeaders) record.headers();
                    Properties properties = KafkaConsumerServer.getProperties(recordHeaders);
                    int retryCount = Integer.parseInt(properties.getProperty(KafkaConstants.RETRY_COUNT));
                    if (retryCount <= propertiesRetryCount) {
                        Long oldTime = record.timestamp();
                        Long rightTime = getNeedRetryTime(record.timestamp(), retryCount);
                        if (rightTime > System.currentTimeMillis()) {
                            ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<>(KafkaConstants.RETRY_TOPIC, null, oldTime, null, String.valueOf(record.value()).getBytes(), recordHeaders);
                            retryProducer.send(producerRecord);
                        } else {
                            ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<>(properties.getProperty(KafkaConstants.TOPIC_CAPTION), null, null, null, String.valueOf(record.value()).getBytes(), recordHeaders);
                            log.info("==============【topic={} 第{}次发送消息重试】：value={}, properties={}\n", properties.getProperty(KafkaConstants.TOPIC_CAPTION), retryCount, record.value(), properties);
                            retryProducer.send(producerRecord);
                        }
                    } else if (retryCount == propertiesRetryCount + 1) {
                        recordHeaders.add(KafkaConstants.RETRY_COUNT, String.valueOf(retryCount).getBytes());
                        recordHeaders.add(KafkaConstants.TOPIC_CAPTION, String.valueOf(properties.getProperty(KafkaConstants.TOPIC_CAPTION)).getBytes());
                        ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<>(KafkaConstants.DEAD_TOPIC, null, null, null, String.valueOf(record.value()).getBytes(), recordHeaders);
                        properties.remove(KafkaConstants.RETRY_COUNT);
                        log.warn("============== 重试{}次后放弃【结束】=======进入死信队列：topic={}, value={}, properties={}\n", propertiesRetryCount, KafkaConstants.DEAD_TOPIC, record.value(), properties);
                        retryProducer.send(producerRecord);
                    }
                }
                retryConsumer.commitSync();
            }
        }
    }

    /**
     * 获取下次重试时间
     *
     * @param timestamp  消息的发送时间
     * @param retryCount 消息的重试次数
     * @return 需要重发消息的时间
     */
    private Long getNeedRetryTime(Long timestamp, int retryCount) {
        Integer[] retryTimeArray = new Integer[]{
                KafkaConstants.RETRY_10_SECOND,
                KafkaConstants.RETRY_30_SECOND,
                KafkaConstants.RETRY_1_MIN,
                KafkaConstants.RETRY_2_MIN,
                KafkaConstants.RETRY_3_MIN,
                KafkaConstants.RETRY_4_MIN,
                KafkaConstants.RETRY_5_MIN,
                KafkaConstants.RETRY_6_MIN,
                KafkaConstants.RETRY_7_MIN,
                KafkaConstants.RETRY_8_MIN,
                KafkaConstants.RETRY_9_MIN,
                KafkaConstants.RETRY_10_MIN,
                KafkaConstants.RETRY_20_MIN,
                KafkaConstants.RETRY_30_MIN,
                KafkaConstants.RETRY_1_H,
                KafkaConstants.RETRY_2_H
        };
        return retryTimeArray[retryCount - 1] + timestamp;
    }
}
