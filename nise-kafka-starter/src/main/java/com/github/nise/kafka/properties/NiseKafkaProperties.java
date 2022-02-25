package com.github.nise.kafka.properties;

import lombok.Data;
import org.springframework.boot.context.properties.*;

/**
 * @author JiangZhe  2/20/22.
 */
@Data
@ConfigurationProperties(prefix = "nise.kafka")
public class NiseKafkaProperties {

    /**
     * kafka的后台服务访问地址.
     */
    private String bootstrapServer;

    /**
     * kafka安全访问协议（公网路由接入才需要）.
     */
    private String securityProtocol;

    /**
     * sasl机制（公网路由接入才需要）.
     */
    private String saslMechanism;

    /**
     * sasl访问策略（公网路由接入才需要）.
     */
    private String saslJaasConfig;

    /**
     * 会话超时时间，单位毫秒
     */
    private Integer sessionTimeoutMs;

    /**
     * 值序列化.
     */
    private String valueSerializerClassConfig;

    /**
     * 键序列化.
     */
    private String keySerializerClassConfig;

    /**
     * 消息发送完的响应机制.
     * 0-消息发送出去即返回成功;
     * 1-消息发送后leader确认即返回成功;
     * -1-消息发送后leader和所有follower都确认才返回成功.
     */
    private String acksConfig;

    /**
     * 消费者组
     */
    private String groupId;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 是否启用延时队列
     */
    private Boolean useDelay;

    /**
     * 是否自动创建topic
     */
    private Boolean autoCreateTopic;


}
