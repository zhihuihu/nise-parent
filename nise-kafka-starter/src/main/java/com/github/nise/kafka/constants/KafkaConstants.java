package com.github.nise.kafka.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author JiangZhe  2/20/22.
 */
public class KafkaConstants {

    public static final String RETRY_TOPIC = "RETRY_TOPIC";
    public static final String DEAD_TOPIC = "DEAD_TOPIC";
    public static final String TOPIC_CAPTION = "topic";
    public static final String RETRY_COUNT = "retry_count";
    public static final Integer RETRY_10_SECOND = 10000;
    public static final Integer RETRY_30_SECOND = 30000;
    public static final Integer RETRY_1_MIN = 60000;
    public static final Integer RETRY_2_MIN = 120000;
    public static final Integer RETRY_3_MIN = 180000;
    public static final Integer RETRY_4_MIN = 240000;
    public static final Integer RETRY_5_MIN = 300000;
    public static final Integer RETRY_6_MIN = 360000;
    public static final Integer RETRY_7_MIN = 420000;
    public static final Integer RETRY_8_MIN = 480000;
    public static final Integer RETRY_9_MIN = 540000;
    public static final Integer RETRY_10_MIN = 600000;
    public static final Integer RETRY_20_MIN = 1200000;
    public static final Integer RETRY_30_MIN = 1800000;
    public static final Integer RETRY_1_H = 3600000;
    public static final Integer RETRY_2_H = 7200000;

    //延时
    public static final Map<String, Long> delayToMap = new HashMap<>();
    static {
        delayToMap.put("DELAY_TOPIC_FIVE_SECOND_TOPIC", 5000L);
        delayToMap.put("DELAY_TOPIC_HALF_MINUTE_TOPIC", 30000L);
        delayToMap.put("DELAY_TOPIC_ONE_MINUTE_TOPIC", 60000L);
        delayToMap.put("DELAY_TOPIC_FIVE_MINUTE_TOPIC", 300000L);
        delayToMap.put("DELAY_TOPIC_TEN_MINUTE_TOPIC", 600000L);
        delayToMap.put("DELAY_TOPIC_HALF_HOUR_TOPIC", 1800000L);
        delayToMap.put("DELAY_TOPIC_ONE_HOUR_TOPIC", 3600000L);
    }

    public static final String DELAY_TOPIC_FIVE_SECOND_TOPIC = "DELAY_TOPIC_FIVE_SECOND_TOPIC";
    public static final String DELAY_TOPIC_HALF_MINUTE_TOPIC = "DELAY_TOPIC_HALF_MINUTE_TOPIC";
    public static final String DELAY_TOPIC_ONE_MINUTE_TOPIC = "DELAY_TOPIC_ONE_MINUTE_TOPIC";
    public static final String DELAY_TOPIC_FIVE_MINUTE_TOPIC = "DELAY_TOPIC_FIVE_MINUTE_TOPIC";
    public static final String DELAY_TOPIC_TEN_MINUTE_TOPIC = "DELAY_TOPIC_TEN_MINUTE_TOPIC";
    public static final String DELAY_TOPIC_HALF_HOUR_TOPIC = "DELAY_TOPIC_HALF_HOUR_TOPIC";
    public static final String DELAY_TOPIC_ONE_HOUR_TOPIC = "DELAY_TOPIC_ONE_HOUR_TOPIC";


    public static Long getTimestamp(String topic) {
        return Optional.ofNullable(delayToMap.entrySet().stream().filter(entry -> entry.getKey().equals(topic)).findFirst().get().getValue()).orElse(null);
    }
}
