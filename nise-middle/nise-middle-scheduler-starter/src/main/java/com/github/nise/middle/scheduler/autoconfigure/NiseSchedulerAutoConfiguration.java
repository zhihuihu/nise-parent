/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.scheduler.autoconfigure;

import com.github.nise.middle.scheduler.config.ScheduledRegistrar;
import com.github.nise.middle.scheduler.properties.NiseSchedulerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/12 11:05 huzhi Exp $$
 */
@Configuration
@ConditionalOnProperty(value = "nise.scheduler.enabled", havingValue = "true")
public class NiseSchedulerAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "nise.scheduler")
    public NiseSchedulerProperties niseSchedulerProperties(){
        return new NiseSchedulerProperties();
    }

    /**
     * 用户可以自己重新定义该线程池，只要名称相同即可
     * @param niseSchedulerProperties
     * @return
     */
    @Bean(name = "niseThreadPoolTaskScheduler")
    @ConditionalOnMissingBean(name = "niseThreadPoolTaskScheduler")
    public ThreadPoolTaskScheduler niseThreadPoolTaskScheduler(NiseSchedulerProperties niseSchedulerProperties) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(niseSchedulerProperties.getPoolSize());
        threadPoolTaskScheduler.setRemoveOnCancelPolicy(niseSchedulerProperties.getRemoveOnCancelPolicy());
        threadPoolTaskScheduler.setThreadNamePrefix(niseSchedulerProperties.getThreadName());
        return threadPoolTaskScheduler;
    }

    @Bean
    public ScheduledRegistrar scheduledRegistrar(ThreadPoolTaskScheduler niseThreadPoolTaskScheduler){
        ScheduledRegistrar scheduledRegistrar = new ScheduledRegistrar(niseThreadPoolTaskScheduler);
        return scheduledRegistrar;
    }
}
