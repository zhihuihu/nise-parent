/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.log.mongo.dto;

import com.github.nise.common.dto.PageSortReq;
import lombok.Data;

/**
 * 操作日志分页查询类
 * @author huzhi
 * @version $ v 0.1 2021/11/18 20:26 huzhi Exp $$
 */
@Data
public class OperateLogPageReq extends PageSortReq {

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

    /** 模糊搜索 */
    private String search;

    /** 创建时间搜索开始 */
    private String createTimeStart;
    /** 创建时间搜索结束 */
    private String createTimeEnd;
}
