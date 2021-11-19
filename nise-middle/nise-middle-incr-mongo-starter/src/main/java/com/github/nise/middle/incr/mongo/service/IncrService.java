/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.middle.incr.mongo.service;

import com.github.nise.common.utils.LambdaUtils;
import com.github.nise.middle.incr.mongo.entity.Incr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 自增ID获取
 * @author huzhi
 * @version $ v 0.1 2021/11/19 21:22 huzhi Exp $$
 */
public class IncrService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取自增ID
     * @param collectionName
     * @return
     */
    public Long getIncrId(String collectionName){
        Query query = new Query(Criteria.where(LambdaUtils.getColumnName(Incr::getCollectionName)).is(collectionName));
        Update update = new Update();
        update.inc(LambdaUtils.getColumnName(Incr::getIncrId));
        FindAndModifyOptions options = FindAndModifyOptions.options();
        options.upsert(true);
        options.returnNew(true);
        Incr incr = mongoTemplate.findAndModify(query,update,options,Incr.class);
        return incr.getIncrId();
    }

}