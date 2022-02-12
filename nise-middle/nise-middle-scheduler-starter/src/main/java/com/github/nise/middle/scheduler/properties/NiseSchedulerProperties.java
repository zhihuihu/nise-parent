/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.scheduler.properties;

import lombok.Data;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/12 9:00 huzhi Exp $$
 */
@Data
public class NiseSchedulerProperties {

    /** 是否开启 */
    private Boolean enabled = false;
    /** 线程数量 */
    private Integer poolSize = 4;
    /** 线程明细 */
    private String threadName = "nise-scheduler-thread";
    /** 任务一旦取消直接移除 */
    private Boolean removeOnCancelPolicy = false;
}
