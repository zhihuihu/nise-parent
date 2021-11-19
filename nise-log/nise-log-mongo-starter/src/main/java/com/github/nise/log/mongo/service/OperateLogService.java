/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.log.mongo.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.github.nise.common.dto.PageInfo;
import com.github.nise.common.utils.LambdaUtils;
import com.github.nise.log.dto.OperateLogInfo;
import com.github.nise.log.mongo.dto.OperateLogPageReq;
import com.github.nise.log.mongo.entity.OperateLogMongo;
import com.github.nise.log.mongo.repository.OperateLogRepositoryImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author huzhi
 * @version $ v 0.1 2021/11/18 20:22 huzhi Exp $$
 */
public class OperateLogService {

    @Autowired
    private OperateLogRepositoryImpl operateLogRepositoryImpl;

    /**
     * 新增
     * @param operateLogInfo
     */
    public void add(OperateLogInfo operateLogInfo){
        OperateLogMongo operateLogMongo = new OperateLogMongo();
        BeanUtils.copyProperties(operateLogInfo,operateLogMongo);
        operateLogMongo.setId(String.valueOf(IdUtil.getSnowflake().nextId()));
        operateLogMongo.setRequestDataStr(JSON.toJSONString(operateLogInfo.getRequestData()));
        operateLogMongo.setResponseDataStr(JSON.toJSONString(operateLogInfo.getResponseData()));
        operateLogMongo.setThrowableStr(JSON.toJSONString(operateLogInfo.getThrowable()));
        operateLogMongo.setCreateTime(LocalDateTime.now());
        operateLogRepositoryImpl.insert(operateLogMongo);
    }

    /**
     * 新增
     * @param operateLogMongo
     */
    public void add(OperateLogMongo operateLogMongo){
        operateLogMongo.setId(String.valueOf(IdUtil.getSnowflake().nextId()));
        operateLogMongo.setCreateTime(LocalDateTime.now());
        operateLogRepositoryImpl.insert(operateLogMongo);
    }

    /**
     * 分页参数
     * @param operateLogPageReq
     * @return
     */
    public PageInfo<OperateLogMongo> page(OperateLogPageReq operateLogPageReq){
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(!ObjectUtils.isEmpty(operateLogPageReq.getLogId())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getLogId)).is(operateLogPageReq.getLogId());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getUserId())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getUserId)).is(operateLogPageReq.getUserId());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getUserName())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getUserName)).is(operateLogPageReq.getUserName());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getUserCname())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getUserCname)).is(operateLogPageReq.getUserCname());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getHttpMethod())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getHttpMethod)).is(operateLogPageReq.getHttpMethod());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getRemoteAddress())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getRemoteAddress)).is(operateLogPageReq.getRemoteAddress());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getUrl())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getUrl)).is(operateLogPageReq.getUrl());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getClassMethod())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getClassMethod)).regex(".*?" + operateLogPageReq.getClassMethod() + ".*?");
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getMethodName())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getMethodName)).is(operateLogPageReq.getMethodName());
        }
        if(null != operateLogPageReq.getRequestSuccess()){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getRequestSuccess)).is(operateLogPageReq.getRequestSuccess());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getRequestDataStr())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getRequestDataStr)).regex(".*?" + operateLogPageReq.getRequestDataStr() + ".*?");
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getResponseDataStr())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getResponseDataStr)).regex(".*?" + operateLogPageReq.getResponseDataStr() + ".*?");
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getThrowableStr())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getThrowableStr)).regex(".*?" + operateLogPageReq.getThrowableStr() + ".*?");
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getCreateTimeStart())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getCreateTime)).gte(operateLogPageReq.getCreateTimeStart());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getCreateTimeEnd())){
            criteria.and(LambdaUtils.getColumnName(OperateLogMongo::getCreateTime)).lte(operateLogPageReq.getCreateTimeEnd());
        }
        if(!ObjectUtils.isEmpty(operateLogPageReq.getSearch())){
            criteria.orOperator(Arrays.asList(Criteria.where(LambdaUtils.getColumnName(OperateLogMongo::getMethodName)).regex(".*?" + operateLogPageReq.getMethodName() + ".*?"),
                    Criteria.where(LambdaUtils.getColumnName(OperateLogMongo::getRequestDataStr)).regex(".*?" + operateLogPageReq.getSearch() + ".*?"),
                    Criteria.where(LambdaUtils.getColumnName(OperateLogMongo::getResponseDataStr)).regex(".*?" + operateLogPageReq.getSearch() + ".*?"),
                    Criteria.where(LambdaUtils.getColumnName(OperateLogMongo::getThrowableStr)).regex(".*?" + operateLogPageReq.getSearch() + ".*?")));
        }
        query.addCriteria(criteria);
        PageInfo<OperateLogMongo> pageInfo = operateLogRepositoryImpl.page(operateLogPageReq,query,OperateLogMongo.class);
        return pageInfo;
    }

}
