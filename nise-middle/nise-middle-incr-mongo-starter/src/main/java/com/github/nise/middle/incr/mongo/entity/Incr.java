/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.middle.incr.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 自增类
 * @author huzhi
 * @version $ v 0.1 2021/11/19 21:21 huzhi Exp $$
 */
@Document
@Data
public class Incr {

    @Id
    private String id;

    private String collectionName;

    private Long incrId;
}