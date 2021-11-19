package com.github.nise.middle.mongo.repository;

import com.github.nise.common.dto.PageInfo;
import com.github.nise.common.dto.PageSortReq;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author huzhi
 * @version $ v 0.1 2021/11/19 7:24 huzhi Exp $$
 */
public interface BaseMongoRepository<T,ID> {

    /**
     * 新增数据
     * @param entity
     */
    void insert(T entity);

    /**
     * 批量插入数据
     * @param list
     */
    void insertAll(List<T> list);

    /**
     * 批量插入数据
     * @param list
     * @param collectionName
     */
    void insertAll(List<T> list, String collectionName);

    /**
     * 更新数据
     * @param entity
     */
    void update(T entity);

    /**
     * 删除所有数据
     * @param entityClass
     */
    void deleteAll(Class<T> entityClass);

    /**
     * 删除列表
     * @param list
     */
    void deleteAll(List<T> list);

    /**
     * 删除指定数据
     * @param entity
     */
    void delete(T entity);

    /**
     * 通过ID删除
     * @param id
     * @param entityClass
     */
    void deleteById(ID id, Class<T> entityClass);

    /**
     * 通过ID查询
     * @param id
     * @param entityClass
     * @return
     */
    T selectById(ID id, Class<T> entityClass);

    /**
     * 通过ID列表查询
     * @param ids
     * @param entityClass
     * @return
     */
    List<T> selectByIds(List<T> ids, Class<T> entityClass);

    /**
     * 通过条件查询
     * @param query
     * @param entityClass
     * @return
     */
    List<T> selectByConditions(Query query, Class<T> entityClass);

    /**
     * 查询所有
     * @param entityClass
     * @return
     */
    List<T> selectAll(Class<T> entityClass);

    /**
     * 查询有多少条
     * @param query
     * @param entityClass
     * @return
     */
    long selectTotalCount(Query query, Class<T> entityClass);

    /**
     * 分页查询
     * @param pageSortReq
     * @param query
     * @param entityClass
     * @return
     */
    PageInfo<T> page(PageSortReq pageSortReq, Query query, Class<T> entityClass);
}
