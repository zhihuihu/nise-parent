package com.github.nise.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.shade.org.apache.commons.codec.Charsets;
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


    public static DelayProduceMessage fromBinary(String topic, byte[] data) {
        return new DelayProduceMessage(topic, data);
    }

    public static DelayProduceMessage fromString(String topic, String data) {
        byte[] binary = data.getBytes(Charsets.UTF_8);
        return new DelayProduceMessage(topic, binary);
    }

    public static DelayProduceMessage fromJSON(String topic, JSONObject data) {
        byte[] binary = JSON.toJSONBytes(data);
        return fromBinary(topic, binary);
    }

    public static DelayProduceMessage fromObject(String topic, Object obj) {
        byte[] binary = JSON.toJSONBytes(obj);
        return fromBinary(topic, binary);
    }
}
