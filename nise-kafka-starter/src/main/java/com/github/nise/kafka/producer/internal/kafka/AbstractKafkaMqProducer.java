package com.github.nise.kafka.producer.internal.kafka;

import com.github.nise.kafka.producer.Producer;
import com.github.nise.kafka.properties.NiseKafkaProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * 抽象生产者
 * @author JiangZhe  2/20/22.
 */
public abstract class AbstractKafkaMqProducer implements Producer {

    @Resource
    private NiseKafkaProperties kafkaProperties;

    public KafkaProducer<String, byte[]> producer;

    public void init() {
        Properties properties = new Properties();
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, null == kafkaProperties.getSessionTimeoutMs() ? 3000 : kafkaProperties.getSessionTimeoutMs());
        //序列化类
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");       // 键的序列化
        properties.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");  // 值的序列化
        // 确认机制
        properties.put("acks", StringUtils.isBlank(kafkaProperties.getAcksConfig()) ? "1" : kafkaProperties.getAcksConfig());
        //重试次数
        properties.put("retries", 0);
        // getBootstrapServer（必填 ）
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServer());
        // 认证（公网）
        if (StringUtils.isNotBlank(kafkaProperties.getSecurityProtocol())) {
            properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
        }
        if (StringUtils.isNotBlank(kafkaProperties.getSaslJaasConfig())) {
            properties.put("sasl.jaas.config", kafkaProperties.getSaslJaasConfig());
        }
        if (StringUtils.isNotBlank(kafkaProperties.getSaslMechanism())) {
            properties.put("sasl.mechanism", kafkaProperties.getSaslMechanism());
        }

        this.producer = new KafkaProducer<>(properties);
    }
}
