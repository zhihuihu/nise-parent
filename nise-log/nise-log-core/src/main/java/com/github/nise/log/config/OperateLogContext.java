/**
 * https://github.com/zhihuihu
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.log.config;

import com.github.nise.log.dto.OperateLogInfo;

/**
 * 日志上下文类
 * @author huzhihui
 * @version $ v 0.1 2021/11/11 7:36 huzhihui Exp $$
 */
public class OperateLogContext {

    /** 保存线程上下文 */
    private static final ThreadLocal<OperateLogInfo> OPERATE_LOG_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static void setOperateLogInfo(OperateLogInfo operateLogInfo){
        OPERATE_LOG_INFO_THREAD_LOCAL.set(operateLogInfo);
    }

    public static OperateLogInfo getOperateLogInfo(){
        return OPERATE_LOG_INFO_THREAD_LOCAL.get();
    }

    public static void removeOperateLogInfo(){
        OPERATE_LOG_INFO_THREAD_LOCAL.remove();
    }
}
