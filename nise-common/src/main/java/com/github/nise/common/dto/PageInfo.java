/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.common.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 * @author huzhi
 * @version $ v 0.1 2021/11/18 20:13 huzhi Exp $$
 */
@Data
public class PageInfo<T> {

    /** 一页大小 */
    protected long size;
    /** 当前页 */
    protected long current;
    /** 总条数 */
    protected long total;
    /** 返回结果 */
    protected List<T> records = new ArrayList<>();

    public PageInfo() {
    }

    /**
     * 有参构造函数
     * @param pageSortReq
     */
    public PageInfo(PageSortReq pageSortReq) {
        this.size = pageSortReq.getSize();
        this.current = pageSortReq.getCurrent();
    }
}
