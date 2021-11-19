/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.middle.mongo.repository;

import com.github.nise.common.constant.NiseConstant;
import com.github.nise.common.dto.PageInfo;
import com.github.nise.common.dto.PageSortReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huzhi
 * @version $ v 0.1 2021/11/19 7:36 huzhi Exp $$
 */
public abstract class BaseMongoRepositoryImpl<T,ID> implements BaseMongoRepository<T,ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insert(T entity) {
        mongoTemplate.insert(entity);
    }

    @Override
    public void insertAll(List<T> list) {
        mongoTemplate.insertAll(list);
    }

    @Override
    public void insertAll(List<T> list, String collectionName) {
        // BulkMode.UNORDERED:表示并行处理，遇到错误时能继续执行不影响其他操作；BulkMode.ORDERED：表示顺序执行，遇到错误时会停止所有执行
        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, collectionName);
        ops.insert(list);
        // 执行操作
        ops.execute();
    }

    @Override
    public void update(T entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public void deleteAll(Class<T> entityClass) {
        mongoTemplate.remove(entityClass);
    }

    @Override
    public void deleteAll(List<T> list) {
        for (Object entity : list) {
            mongoTemplate.remove(entity);
        }
    }

    @Override
    public void delete(T entity) {
        mongoTemplate.remove(entity);
    }

    @Override
    public void deleteById(ID id, Class<T> entityClass) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));

        mongoTemplate.remove(query, entityClass);
    }

    @Override
    public T selectById(ID id, Class<T> entityClass) {
        return mongoTemplate.findById(id, entityClass);
    }

    @Override
    public List<T> selectByIds(List<T> ids, Class<T> entityClass) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").in(ids));
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public List<T> selectByConditions(Query query, Class<T> entityClass) {
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public List<T> selectAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    @Override
    public long selectTotalCount(Query query, Class<T> entityClass) {
        return mongoTemplate.count(query, entityClass);
    }

    @Override
    public PageInfo<T> page(PageSortReq pageSortReq, Query query, Class<T> entityClass) {
        //获取总条数
        long totalCount = this.selectTotalCount(query, entityClass);

        long skip = (pageSortReq.getCurrent() - 1) * pageSortReq.getSize();

        if (!CollectionUtils.isEmpty(pageSortReq.getSorts())) {
            List<Sort.Order> orders = new ArrayList<>();
            for (PageSortReq.SortParam sort : pageSortReq.getSorts()) {
                Sort.Order order = new Sort.Order((!ObjectUtils.isEmpty(sort.getOrder().toUpperCase()) && sort.getOrder().toUpperCase().equals(NiseConstant.ASC)) ? Sort.Direction.ASC : Sort.Direction.DESC,sort.getParam());
                orders.add(order);
            }
            query.with(Sort.by(orders));
        }

        query.skip(skip);
        query.limit(pageSortReq.getSize().intValue());
        List<T> dataList = mongoTemplate.find(query, entityClass);
        PageInfo<T> page = new PageInfo<>(pageSortReq);
        //获取数据
        page.setTotal(totalCount);
        page.setRecords(dataList);
        return page;
    }
}
