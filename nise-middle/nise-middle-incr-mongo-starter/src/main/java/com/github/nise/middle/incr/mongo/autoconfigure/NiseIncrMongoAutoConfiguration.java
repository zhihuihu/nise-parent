/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.middle.incr.mongo.autoconfigure;

import com.github.nise.middle.incr.mongo.properties.NiseIncrMongoProperties;
import com.github.nise.middle.incr.mongo.service.IncrService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huzhi
 * @version $ v 0.1 2021/11/19 21:25 huzhi Exp $$
 */
@Configuration
@ConditionalOnProperty(value = "nise.incr.mongo.enabled", havingValue = "true")
public class NiseIncrMongoAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "nise.incr.mongo")
    public NiseIncrMongoProperties niseIncrMongoProperties(){
        return new NiseIncrMongoProperties();
    }

    @Bean
    public IncrService incrService(){
        return new IncrService();
    }

}
