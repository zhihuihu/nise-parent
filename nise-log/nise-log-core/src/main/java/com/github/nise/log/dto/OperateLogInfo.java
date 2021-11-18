/**
 * https://github.com/zhihuihu
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.log.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 日志信息
 * @author huzhihui
 * @version $ v 0.1 2021/11/11 7:21 huzhihui Exp $$
 */
@Data
public class OperateLogInfo {

    /** 日志ID */
    private String logId;
    /** 用户ID */
    private String userId;
    /** 用户名称 */
    private String userName;
    /** 用户中文名 */
    private String userCname;
    /** 请求方式 */
    private String httpMethod;
    /** 调用地址 */
    private String remoteAddress;
    /** 请求URL */
    private String url;
    /** 调用的具体方法 */
    private String classMethod;
    /** 请求数据类型 */
    private String contentType;
    /** 方法名称 */
    private String methodName;
    /** 是否请求成功 */
    private Boolean requestSuccess;
    /** 请求数据 */
    private JSONObject requestData;
    /** 返回数据 */
    private Object responseData;
    /** 异常信息 */
    private Throwable throwable;

}
