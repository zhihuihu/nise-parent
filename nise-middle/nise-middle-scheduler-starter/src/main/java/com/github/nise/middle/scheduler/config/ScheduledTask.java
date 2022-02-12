/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.scheduler.config;

import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务
 * @author huzhi
 * @version $ v 0.1 2022/2/12 11:08 huzhi Exp $$
 */
public class ScheduledTask {

    /** 任务名称 */
    public String name;
    /** 定时表达式 */
    public String cronExpression;
    /** 任务执行体 */
    public Runnable runnable;
    /** 提交任务后的future */
    public volatile ScheduledFuture<?> scheduledFuture;

    /**
     * 取消任务
     */
    public void cancel(){
        ScheduledFuture<?> scheduledFuture = this.scheduledFuture;
        if(null != scheduledFuture){
            scheduledFuture.cancel(true);
        }
    }


}
