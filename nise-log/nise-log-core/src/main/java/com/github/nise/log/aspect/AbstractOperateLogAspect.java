/**
 * https://github.com/zhihuihu
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.log.aspect;

import com.alibaba.fastjson.JSONObject;
import com.github.nise.log.config.OperateLogContext;
import com.github.nise.log.dto.OperateLogInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        String contentType = request.getContentType();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        OperateLogInfo operateLogInfo = new OperateLogInfo();
        operateLogInfo.setLogId(MDC.get("tranceId"));
        operateLogInfo.setHttpMethod(method);
        operateLogInfo.setRemoteAddress(remoteAddr);
        operateLogInfo.setUrl(requestURI);
        operateLogInfo.setContentType(contentType);
        operateLogInfo.setClassMethod(classMethod);
        operateLogInfo.setRequestData(this.buildRequestData(joinPoint));
        OperateLogContext.setOperateLogInfo(operateLogInfo);
        // log.info("http request id:" + operateLogInfo.getLogId() + " allData" + JSON.toJSONString(operateLogInfo));
    }

    /**
     *
     * @param joinPoint
     * @param ref
     * @return
     */
    public void doAfterReturning(JoinPoint joinPoint, Object ref){
        OperateLogInfo operateLogInfo = OperateLogContext.getOperateLogInfo();
        operateLogInfo.setResponseData(ref);
        // log.info("http response id:" + operateLogInfo.getLogId() + " data:" + JSON.toJSONString(ret));
    }

    /**
     *
     * @param joinPoint
     * @param e
     * @return
     */
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e){
        OperateLogInfo operateLogInfo = OperateLogContext.getOperateLogInfo();
        operateLogInfo.setThrowable(e);
    }

    /**
     * 获取请求数据
     * @param joinPoint
     * @return
     */
    private JSONObject buildRequestData(JoinPoint joinPoint){
        JSONObject jsonObject = new JSONObject();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if(!(args[i] instanceof HttpServletRequest ) && !(args[i] instanceof HttpServletResponse) && !(args[i] instanceof MultipartFile)){
                jsonObject.put(parameterNames[i],args[i]);
            }
        }
        return jsonObject;
    }
}
