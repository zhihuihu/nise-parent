package com.github.nise.kafka.autoconfigure;

import com.github.nise.kafka.consumer.internal.kafka.KafkaConsumerServer;
import com.github.nise.kafka.producer.internal.kafka.KafkaProducerServer;
import com.github.nise.kafka.properties.NiseKafkaProperties;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot Starter初始化
 * 1. 将EnableConfigurationProperties 加载到Spring上下文的容器中
 * 2. 当配置文件存在“nise.kafka.enabled=true”时新建对象
 * @author JiangZhe  2/20/22.
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties(NiseKafkaProperties.class)
@ConditionalOnClass({KafkaProducerServer.class, KafkaConsumer.class})
@ConditionalOnProperty(value = "nise.kafka.enabled", havingValue = "true")
public class KafkaAutoConfiguration {

    @Bean(initMethod = "init")
    public KafkaProducerServer kafkaProducerServer() {
        return new KafkaProducerServer();
    }

    @Bean(initMethod = "init")
    public KafkaConsumerServer kafkaConsumerServer() {
        return new KafkaConsumerServer();
    }
}
