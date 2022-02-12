/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.scheduler.config;

import com.github.nise.common.enums.HttpCode;
import com.github.nise.common.utils.AssertUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/12 11:25 huzhi Exp $$
 */
public class ScheduledRegistrar {

    /** 定义的任务缓存 */
    private final Map<String, ScheduledTask> scheduledTaskMap = new ConcurrentHashMap<>(16);

    private ThreadPoolTaskScheduler niseThreadPoolTaskScheduler;

    public ScheduledRegistrar(ThreadPoolTaskScheduler niseThreadPoolTaskScheduler) {
        this.niseThreadPoolTaskScheduler = niseThreadPoolTaskScheduler;
    }

    /**
     * 由于不存在大量并发，所以简单同步处理
     * 新增定时任务
     * @param taskName
     * @param cronExpression
     * @param task
     */
    public synchronized void addCronTask(String taskName, String cronExpression, Runnable task){
        AssertUtils.isTrue(!ObjectUtils.isEmpty(taskName), HttpCode.FAIL.getCode(),"定时任务名称为空");
        AssertUtils.isTrue(!ObjectUtils.isEmpty(cronExpression), HttpCode.FAIL.getCode(),"定时任务表达式为空");
        AssertUtils.isTrue(!ObjectUtils.isEmpty(task), HttpCode.FAIL.getCode(),"定时任务为空");
        AssertUtils.isTrue(!scheduledTaskMap.containsKey(taskName), HttpCode.FAIL.getCode(),taskName + "任务已在列表中");
        CronTask cronTask = new CronTask(task, cronExpression);
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.scheduledFuture = this.niseThreadPoolTaskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        scheduledTask.name = taskName;
        scheduledTask.cronExpression = cronExpression;
        scheduledTaskMap.put(taskName,scheduledTask);
    }

    /**
     * 由于不存在大量并发，所以简单同步处理
     * 通过任务名称删除任务
     * @param taskName
     */
    public synchronized void removeCronTask(String taskName){
        ScheduledTask scheduledTask = this.scheduledTaskMap.remove(taskName);
        if(null != scheduledTask){
            scheduledTask.cancel();
        }
    }

}
