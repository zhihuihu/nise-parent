/**
 * https://github.com/zhihuihu
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.log.aspect;

import com.github.nise.log.config.OperateLogContext;
import com.github.nise.log.dto.OperateLogInfo;
import org.aspectj.lang.JoinPoint;

/**
 * 基础抽象类 日志执行
 * @author huzhihui
 * @version $ v 0.1 2021/11/11 7:31 huzhihui Exp $$
 */
public class AbstractOperateLogAspect {

    /**
     *
     * @param joinPoint
     * @return
     */
    public OperateLogInfo doBefore(JoinPoint joinPoint){
        return OperateLogContext.getOperateLogInfo();
    }

    /**
     *
     * @param joinPoint
     * @param ref
     * @return
     */
    public OperateLogInfo doAfterReturning(JoinPoint joinPoint, Object ref){
        return OperateLogContext.getOperateLogInfo();
    }

    /**
     *
     * @param joinPoint
     * @param e
     * @return
     */
    public OperateLogInfo doAfterThrowing(JoinPoint joinPoint, Throwable e){
        return OperateLogContext.getOperateLogInfo();
    }
}
