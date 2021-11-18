/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.log.mongo.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huzhi
 * @version $ v 0.1 2021/11/18 20:17 huzhi Exp $$
 */
@Data
@Document("operateLog")
public class OperateLogMongo implements Serializable {

    @Id
    private String id;
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
    private String requestDataStr;
    /** 返回数据 */
    private String responseDataStr;
    /** 异常信息 */
    private String throwableStr;
    /** 日志时间 */
    private LocalDateTime createTime;

}
