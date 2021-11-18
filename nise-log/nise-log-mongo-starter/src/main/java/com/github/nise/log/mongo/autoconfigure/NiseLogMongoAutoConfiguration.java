/**
 * https://github.com/zhihuihu
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.log.mongo.autoconfigure;

import com.github.nise.log.mongo.properties.NiseLogMongoProperties;
import com.github.nise.log.mongo.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author huzhihui
 * @version $ v 0.1 2021/11/14 20:54 huzhihui Exp $$
 */
@Configuration
@ConditionalOnProperty(value = "nise.log.mongo.enabled", havingValue = "true")
public class NiseLogMongoAutoConfiguration {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Bean
    @ConfigurationProperties(prefix = "nise.log.mongo")
    public NiseLogMongoProperties niseLogMongoProperties(){
        return new NiseLogMongoProperties();
    }

    @Bean
    public OperateLogService operateLogService(MongoTemplate mongoTemplate){
        return new OperateLogService(mongoTemplate);
    }
}
