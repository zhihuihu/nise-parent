package com.github.nise.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.shade.org.apache.commons.codec.Charsets;
import com.github.nise.kafka.constants.KafkaConstants;
import lombok.Data;

/**
 * @author JiangZhe  2/20/22.
 */
@Data
public class DelayProduceMessage {

    /**
     * 消息所属的主题
     */
    private final String topic;
    /**
     * 消息体的二进制数据
     */
    private final byte[] payload;

    private DelayProduceMessage(String topic, byte[] data) {
        this.topic = topic;
        this.payload = data;
    }

    /**
     * 5秒钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage fiveSecondFromBinary(byte[] data) {
        return fromBinary(KafkaConstants.DELAY_TOPIC_FIVE_SECOND_TOPIC, data);
    }

    /**
     * 半分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage halfMinuteFromBinary(byte[] data) {
        return fromBinary(KafkaConstants.DELAY_TOPIC_HALF_MINUTE_TOPIC, data);
    }

    /**
     * 1分总延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage oneMinuteFromBinary(byte[] data) {
        return fromBinary(KafkaConstants.DELAY_TOPIC_ONE_MINUTE_TOPIC, data);
    }

    /**
     * 10分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage tenMinuteFromBinary(byte[] data) {
        return fromBinary(KafkaConstants.DELAY_TOPIC_TEN_MINUTE_TOPIC, data);
    }

    /**
     * 半小时延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage halfHourFromBinary(byte[] data) {
        return fromBinary(KafkaConstants.DELAY_TOPIC_HALF_HOUR_TOPIC, data);
    }

    /**
     * 1小时延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage oneHourFromBinary(byte[] data) {
        return fromBinary(KafkaConstants.DELAY_TOPIC_ONE_HOUR_TOPIC, data);
    }

    /**
     * 5秒钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage fiveSecondFromString(String data) {
        return fromString(KafkaConstants.DELAY_TOPIC_FIVE_SECOND_TOPIC, data);
    }

    /**
     * 半分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage halfMinuteFromString(String data) {
        return fromString(KafkaConstants.DELAY_TOPIC_HALF_MINUTE_TOPIC, data);
    }

    /**
     * 1分总延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage oneMinuteFromString(String data) {
        return fromString(KafkaConstants.DELAY_TOPIC_ONE_MINUTE_TOPIC, data);
    }

    /**
     * 5分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage fiveMinuteFromString(String data) {
        return fromString(KafkaConstants.DELAY_TOPIC_FIVE_MINUTE_TOPIC, data);
    }

    /**
     * 10分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage tenMinuteFromString(String data) {
        return fromString(KafkaConstants.DELAY_TOPIC_TEN_MINUTE_TOPIC, data);
    }

    /**
     * 半小时延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage halfHourFromString(String data) {
        return fromString(KafkaConstants.DELAY_TOPIC_HALF_HOUR_TOPIC, data);
    }

    /**
     * 1小时延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage oneHourFromString(String data) {
        return fromString(KafkaConstants.DELAY_TOPIC_ONE_HOUR_TOPIC, data);
    }


    /**
     * 5秒钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage fiveSecondFromJSON(JSONObject data) {
        return fromJSON(KafkaConstants.DELAY_TOPIC_FIVE_SECOND_TOPIC, data);
    }

    /**
     * 半分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage halfMinuteFromJSON(JSONObject data) {
        return fromJSON(KafkaConstants.DELAY_TOPIC_HALF_MINUTE_TOPIC, data);
    }

    /**
     * 1分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage oneMinuteFromJSON(JSONObject data) {
        return fromJSON(KafkaConstants.DELAY_TOPIC_ONE_MINUTE_TOPIC, data);
    }

    /**
     * 5分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage fiveMinuteFromJSON(JSONObject data) {
        return fromJSON(KafkaConstants.DELAY_TOPIC_FIVE_MINUTE_TOPIC, data);
    }

    /**
     * 10分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage tenMinuteFromJSON(JSONObject data) {
        return fromJSON(KafkaConstants.DELAY_TOPIC_TEN_MINUTE_TOPIC, data);
    }

    /**
     * 半小时延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage halfHourFromJSON(JSONObject data) {
        return fromJSON(KafkaConstants.DELAY_TOPIC_HALF_HOUR_TOPIC, data);
    }

    /**
     * 1小时延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage oneHourFromJSON(JSONObject data) {
        return fromJSON(KafkaConstants.DELAY_TOPIC_ONE_HOUR_TOPIC, data);
    }



    /**
     * 5秒钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage fiveSecondFromObject(Object data) {
        return fromObject(KafkaConstants.DELAY_TOPIC_FIVE_SECOND_TOPIC, data);
    }

    /**
     * 半分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage halfMinuteFromObject(Object data) {
        return fromObject(KafkaConstants.DELAY_TOPIC_HALF_MINUTE_TOPIC, data);
    }

    /**
     * 1分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage oneMinuteFromObject(Object data) {
        return fromObject(KafkaConstants.DELAY_TOPIC_ONE_MINUTE_TOPIC, data);
    }

    /**
     * 5分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage fiveMinuteFromObject(Object data) {
        return fromObject(KafkaConstants.DELAY_TOPIC_FIVE_MINUTE_TOPIC, data);
    }

    /**
     * 10分钟延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage tenMinuteFromObject(Object data) {
        return fromObject(KafkaConstants.DELAY_TOPIC_TEN_MINUTE_TOPIC, data);
    }

    /**
     * 半小时延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage halfHourFromObject(Object data) {
        return fromObject(KafkaConstants.DELAY_TOPIC_HALF_HOUR_TOPIC, data);
    }

    /**
     * 1小时延时队列
     *
     * @param data
     * @return
     */
    public static DelayProduceMessage oneHourFromObject(Object data) {
        return fromObject(KafkaConstants.DELAY_TOPIC_ONE_HOUR_TOPIC, data);
    }


    private static DelayProduceMessage fromBinary(String topic, byte[] data) {
        return new DelayProduceMessage(topic, data);
    }

    private static DelayProduceMessage fromString(String topic, String data) {
        byte[] binary = data.getBytes(Charsets.UTF_8);
        return new DelayProduceMessage(topic, binary);
    }

    private static DelayProduceMessage fromJSON(String topic, JSONObject data) {
        byte[] binary = JSON.toJSONBytes(data);
        return fromBinary(topic, binary);
    }

    private static DelayProduceMessage fromObject(String topic, Object obj) {
        byte[] binary = JSON.toJSONBytes(obj);
        return fromBinary(topic, binary);
    }
}
